package com.yiyun.web.manager.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.MemberConstants;
import com.yiyun.constants.MessageEnums;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.dao.cluster.ClusterSmsSendLogDao;
import com.yiyun.dao.master.SmsSendLogDao;
import com.yiyun.dao.master.UserRoleDao;
import com.yiyun.domain.*;
import com.yiyun.resp.SendMessageResp;
import com.yiyun.utils.DataUtil;
import com.yiyun.utils.DateUtil;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.info.SmsManager;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.common.utils.ShiroUtils;
import com.yiyun.web.query.service.IdcardService;
import com.yiyun.web.query.service.LoanService;
import com.yiyun.web.query.service.MMemberService;
import com.yiyun.web.rabbitMQ.util.SendMqUtil;
import com.yiyun.web.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @title 商务岗订单
 * @author Xingbz
 * @createDate 2018-6-9
 */
@Controller
@RequestMapping("/manager/cloan")
public class CommerceLoanController extends BaseController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private DictService dictService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private IdcardService idcardService;

    @Autowired
    private SmsSendLogDao smsSendLogDao;

    @Autowired
    private MMemberService memberService;

    @Autowired
    private ClusterSmsSendLogDao clusterSmsSendLogDao;

    @GetMapping()
    @RequiresPermissions("manager:cloan:cloan")
    public String Loan(Integer type, Model model) {
        model.addAttribute("type", type);
        Map<String, Object> param = new HashMap<>();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.DICT_TYPE_CITYS);
        model.addAttribute("cityList", dictService.list(param));

        List<DictDO> statusList = new ArrayList<>();
        if (type == 2) {
            statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_DBCZL), Loan.LOAN_STATUS_DBCZL + ""));
            statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_ZLBCZ), Loan.LOAN_STATUS_ZLBCZ + ""));
            statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FWJXZ), Loan.LOAN_STATUS_FWJXZ + ""));
        } else if (type == 3) {
            statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FKCG), Loan.LOAN_STATUS_FKCG + ""));
            statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FKSB), Loan.LOAN_STATUS_FKSB + ""));
        }
        model.addAttribute("statusList", statusList);


        List<UserDO> userList = new ArrayList<>();
        Map<String, Object> userParam = new HashMap<>();
        userParam.put("code", SysDictConstants.COMMERCE);
        UserDO uSerDO = ShiroUtils.getUser();
        if (null != uSerDO && StringUtils.isNotBlank(uSerDO.getCity())) {
            userParam.put("city", uSerDO.getCity());
        }

        List<UserDO> resultList = userService.getUserByRoleCode(userParam);//查询商务岗
        if (null != resultList && !resultList.isEmpty()) {
            resultList.forEach(entity -> {
                UserDO userDO = new UserDO();
                userDO.setUserId(entity.getUserId());
                userDO.setName(entity.getName());
                userList.add(userDO);
            });
        }
        model.addAttribute("userList", userList);


        return "manager/cloan/cloan";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("manager:cloan:cloan")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        /* 当前查询类型 */
        String typeStr = params.get("type") + "";
        if (StringUtils.isNotBlank(typeStr) && !"null".equals(typeStr) && !"undefined".equals(typeStr)) {
            Integer type = Integer.valueOf(typeStr);//查询类型
            if (type == 1) {//1 未处理 状态为受理成功
                params.put("orderStatus", Loan.LOAN_STATUS_SLCG);
            } else if (type == 2) {//2 处理中 待补充资料/资料补充中/服务进行中/
                List<Integer> orderStatusList = new ArrayList<>();
                orderStatusList.add(Loan.LOAN_STATUS_DBCZL);
                orderStatusList.add(Loan.LOAN_STATUS_ZLBCZ);
                orderStatusList.add(Loan.LOAN_STATUS_FWJXZ);
                params.put("orderStatusList", orderStatusList);
            } else if (type == 3) {//已处理 放款成功/放款失败
                List<Integer> orderStatusList = new ArrayList<>();
                orderStatusList.add(Loan.LOAN_STATUS_FKCG);
                orderStatusList.add(Loan.LOAN_STATUS_FKSB);
                params.put("orderStatusList", orderStatusList);
            }
        }

        /* 当前查询角色 */
        //已处理订单 , 商务岗只能看自己处理的
        UserDO user = ShiroUtils.getUser();
        if (null != user && StringUtils.isNotBlank(user.getCity())) {//只看同属城市的
            params.put("city", user.getCity());
        }
        List<Long> roleIds = user.getRoleIds();//当前用户拥有的角色
        if (null == roleIds || roleIds.isEmpty()) {
            roleIds = userRoleDao.listRoleId(user.getUserId());
        }

        if (!containsSuper(roleIds) && containsCommerce(roleIds)) {//不包含超级管理员且包含商务
            params.put("column4", user.getUserId());
        }

        /* 时间处理 */
        String operateTime = getParamString(params, "operateTime");
        if (StringUtils.isNotBlank(operateTime) && operateTime.contains("~")) {//2018-06-20~2018-07-26
            String[] timeArr = operateTime.split("~");
            if (timeArr.length > 0) {
                String timeBeginStr = timeArr[0].trim();
                String timeBeginEnd = timeArr[1].trim();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Long timeBeginLong = LocalDateTime.of(LocalDate.parse(timeBeginStr, dtf), LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
                Long timeEndLong = LocalDateTime.of(LocalDate.parse(timeBeginEnd, dtf), LocalTime.MIN).plusDays(1).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
                params.put("operateTimeBegin", timeBeginLong);
                params.put("operateTimeEnd", timeEndLong);
            }
        }

        //查询列表数据
        Query query = new Query(params);
        List<Loan> loanList = loanService.list(query);
        if (null != loanList && !loanList.isEmpty()) {
            loanList.forEach(entity -> {
                entity.setColumn1(StringUtils.isBlank(entity.getColumn1()) ? "000000" : entity.getColumn1());
                entity.setName(DataUtil.starsName(entity.getName()));
                entity.setIdcard(DataUtil.starsIdcard(entity.getIdcard()));
                entity.setLoanMemberPhone(DataUtil.starsPhone(entity.getLoanMemberPhone()));
            });
        }

        int total = loanService.count(query);
        return new PageUtil(loanList, total);
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("manager:cloan:cloan")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("isSuper", isSuper());

        UserDO userDO = ShiroUtils.getUser();
        model.addAttribute("loginUserName", userDO.getName());
        Loan loan = loanService.get(id);
        Long loanMemberId = loan.getLoanMemberId();

        if (null != loanMemberId) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("memberId", loanMemberId);

            List<Idcard> idCardList = idcardService.list(paramMap);
            if (null != idCardList && !idCardList.isEmpty()) {
                Idcard idCard = idCardList.get(0);
                if (null != idCard) {
                    loan.setName(idCard.getName());
                    loan.setIdcard(idCard.getIdcard());
                }
            }
        }

        loan.setName(DataUtil.starsName(loan.getName()));
        loan.setIdcard(DataUtil.starsIdcard(loan.getIdcard()));
        loan.setLoanMemberPhone(DataUtil.starsPhone(loan.getLoanMemberPhone()));
        loan.setWechatPhone(DataUtil.starsPhone(loan.getWechatPhone()));

        model.addAttribute("loan", loan);
        return "manager/cloan/edit";
    }

    /**
     * 处理
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("manager:cloan:cloan")
    public R update(Loan loan) throws ParseException {
        if (isSuper() == CommonConstants.YES) {//管理员角色不能处理
            return R.error("管理员不能介入操作");
        }
        if (null == loan) {
            return R.error("更新订单失败");
        }

        //<editor-fold desc="数据设值">
        loan.setCustomerTime(Instant.now().toEpochMilli());//商务处理时间
        loan.setColumn2(Instant.now().toEpochMilli() + "");//最近更新时间
        if (Objects.equals(Loan.LOAN_STATUS_FKCG, loan.getOrderStatus()) && null != loan.getRealAmount()) {//放款时间
            loan.setLoanTime(Instant.now().toEpochMilli());
        }

        /* 时间 */
        String loanBeginTimeStr = loan.getLoanBeginTimeStr();
        String loanEndTimeStr = loan.getLoanEndTimeStr();
        if (StringUtils.isNotBlank(loanBeginTimeStr)) {
            loan.setLoanBeginTime(DateUtil.stringToLong(loanBeginTimeStr, DateUtil.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(loanEndTimeStr)) {
            loan.setLoanEndTime(DateUtil.stringToLong(loanEndTimeStr, DateUtil.YYYY_MM_DD));
        }
        //脱敏数据不更新
        if (DataUtil.isStar(loan.getName())) {
            loan.setName(null);
        }
        if (DataUtil.isStar(loan.getIdcard())) {
            loan.setIdcard(null);
        }
        if (DataUtil.isStar(loan.getLoanMemberPhone())) {
            loan.setLoanMemberPhone(null);
        }
        //</editor-fold>

        UserDO userDO = ShiroUtils.getUser();
        List<Long> roleIds = userDO.getRoleIds();//当前用户拥有的角色
        if (null == roleIds || roleIds.isEmpty()) {
            roleIds = userRoleDao.listRoleId(userDO.getUserId());
        }
        if (!containsSuper(roleIds) && containsCommerce(roleIds)) {//商务角色操作有效
            loan.setColumn4(userDO.getUserId() + "");
            loan.setCustomer(userDO.getName());
        }

        if (StringUtils.isNotBlank(loan.getName()) || StringUtils.isNotBlank(loan.getIdcard()) || StringUtils.isNotBlank(loan.getWechatPhone())) { //保存身份信息
            Long memberId = loan.getLoanMemberId();
            if (null == memberId) { //没有客户信息
                MMember member = new MMember();
                member.setType(MemberConstants.M_TYPE_KH);//客户
                member.setStatus(CommonConstants.YES);//状态正常
                if (StringUtils.isNotBlank(loan.getWechatPhone())) {//借款人手机号
                    member.setNickName(DataUtil.starsPhone(loan.getWechatPhone()));
                    member.setPhone(loan.getWechatPhone());
                }
                member.setName(loan.getName());
                member.setSource(CommonConstants.SOURCE_MAPP);
                member.setCreateTime(Instant.now().toEpochMilli());
                member.setMemberInvestCode("000000");
                member.setInvestCount(0);
                member.setInvestLoanCount(0);

                String investCode = loan.getColumn1();
                member.setInvesterCode(investCode);
                if (!"000000".equals(investCode)) {
                    MMember investMember = memberService.getByInvestCode(investCode);//主邀请人
                    if (null != investMember) {
                        member.setInvesterMemberId(investMember.getId());
                    }
                }
                member.setInvesterCode(loan.getColumn1());
                member = memberService.saveAndReturn(member);
                loan.setLoanMemberId(member.getId());
                if (StringUtils.isNotBlank(loan.getName()) || StringUtils.isNotBlank(loan.getIdcard())) {
                    Idcard newIdcard = new Idcard();
                    newIdcard.setMemberId(member.getId());
                    newIdcard.setName(loan.getName());
                    String idcardNo = loan.getIdcard();
                    newIdcard.setIdcard(idcardNo);
                    newIdcard.setGender(DateUtil.getGenderByIdCard(idcardNo));
                    newIdcard.setAge(DateUtil.ageFromBirthDay(DateUtil.birthdayFromIdCard(idcardNo)));
                    newIdcard.setAdress("");
                    newIdcard.setCreateTime(Instant.now().toEpochMilli());
                    idcardService.save(newIdcard);
                }
            } else {
                Map<String, Object> param = new HashMap<>();
                param.put("memberId", memberId);
                List<Idcard> idcardList = idcardService.list(param);
                if (null == idcardList || idcardList.isEmpty()) { //没有记录
                    Idcard newIdcard = new Idcard();
                    newIdcard.setMemberId(memberId);
                    newIdcard.setName(loan.getName());
                    String idcardNo = loan.getIdcard();
                    newIdcard.setIdcard(idcardNo);
                    newIdcard.setGender(DateUtil.getGenderByIdCard(idcardNo));
                    newIdcard.setAge(DateUtil.ageFromBirthDay(DateUtil.birthdayFromIdCard(idcardNo)));
                    newIdcard.setAdress("");
                    newIdcard.setCreateTime(Instant.now().toEpochMilli());
                    idcardService.save(newIdcard);
                } else { //有记录 , 则更新
                    Idcard oldIdcard = idcardList.get(0);
                    oldIdcard.setName(loan.getName());
                    String idcardNo = loan.getIdcard();
                    oldIdcard.setIdcard(idcardNo);
                    oldIdcard.setGender(DateUtil.getGenderByIdCard(idcardNo));
                    oldIdcard.setAge(DateUtil.ageFromBirthDay(DateUtil.birthdayFromIdCard(idcardNo)));
                    idcardService.update(oldIdcard);
                }
            }
        }

        loanService.update(loan);
        if (Loan.LOAN_STATUS_DBCZL.equals(loan.getOrderStatus())) {
            //更新之后 , 短信通知
            MMember member = memberService.get(loan.getPartner());
            if (null != member) {
                SendMqUtil.sendMessage3(member.getPhone(), member.getName());
            }
        }
        return R.ok();
    }

    @GetMapping("/sendMsg/{id}")
    @RequiresPermissions("manager:cloan:cloan")
    public String sendMsg(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);

        List<MessageEnums> msgList = new ArrayList<>();
        msgList.add(MessageEnums.A);
        msgList.add(MessageEnums.B);
        msgList.add(MessageEnums.C);
        msgList.add(MessageEnums.D);

        msgList.forEach(enu -> {
            String showMsg = enu.getShowMsg();
            enu.setShowMsg(showMsg.replace("{s}", "<span style='color:red'>填写</span>"));
        });

        model.addAttribute("msgList", msgList);
        return "manager/cloan/sendMsg";
    }

    @GetMapping("/sendLog/{id}")
    @RequiresPermissions("manager:cloan:cloan")
    public String sendLog(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);

        Map<String, Object> param = new HashMap<>();
        param.put("isShow", CommonConstants.YES);
        param.put("loanId", id);

        List<SmsSendLog> sendLogs = clusterSmsSendLogDao.list(param);
        model.addAttribute("logs", sendLogs);
        return "manager/cloan/sendLog";
    }

    @GetMapping("/showImg")
    @RequiresPermissions("manager:cloan:cloan")
    public String showImg(Long id, Integer type, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("type", type);

        if (null != id && null != type) {
            Loan dbLoan = loanService.get(id);
            if (null != dbLoan) {
                String imgArrStr = null;
                if (type == 1) {//订单照片
                    imgArrStr = dbLoan.getPhoto();
                } else if (type == 2) {//其他补充资料
                    imgArrStr = dbLoan.getOtherData();
                }
                if (StringUtils.isNotBlank(imgArrStr)) {
                    imgArrStr = imgArrStr.replace("[", "").replace("]", "").replace("\\", "").replace("\"", "");
                    if (StringUtils.isNotBlank(imgArrStr)) {
                        String[] imgArr = imgArrStr.split(",");
                        model.addAttribute("imgArr", imgArr);
                    }
                }
            }
        }

        return "manager/cloan/showImg";
    }

    @ResponseBody
    @PostMapping("/sendConfirm")
    @RequiresPermissions("manager:cloan:cloan")
    public R sendConfirm(Long id, String content) {
        Loan loan = loanService.get(id);
        if (null == loan) {
            return R.error("发送失败 , 订单异常");
        }
        String phone = loan.getLoanMemberPhone();
        if (StringUtils.isBlank(phone)) {
            return R.error("发送失败 , 订单手机号异常");
        }
        SendMessageResp resp = SmsManager.sendMessage(phone, content);//new SendMessageResp();//
//        resp.setFlag(RandomUtil.randomInt() % 2 == 0);
//        resp.setReturnResult("测试短信记录 , 随机返回成功或失败");

        //记录
        SmsSendLog log = new SmsSendLog();
        log.setLoanId(id);
        log.setToPhone(loan.getLoanMemberPhone());
        log.setContent(content);
        log.setStatus(resp.isFlag() ? 1 : 2);

        UserDO loginUser = ShiroUtils.getUser();
        if (null != loginUser) {
            log.setSenderId(loginUser.getUserId());
            log.setSenderName(loginUser.getName());
        }
        log.setRetMsg(resp.getReturnResult());
        log.setCreateTime(Instant.now().toEpochMilli());
        log.setIsShow(CommonConstants.YES);
        smsSendLogDao.save(log);

        if (resp.isFlag()) {
            return R.ok();
        } else {
            return R.error(resp.getReturnResult());
        }
    }
}