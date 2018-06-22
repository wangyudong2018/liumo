package com.yiyun.web.manager.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.dao.master.UserRoleDao;
import com.yiyun.domain.DictDO;
import com.yiyun.domain.Loan;
import com.yiyun.domain.UserDO;
import com.yiyun.utils.DataUtil;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.common.utils.ShiroUtils;
import com.yiyun.web.query.service.LoanService;
import com.yiyun.web.query.service.MMemberService;
import com.yiyun.web.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @title 运营岗订单
 * @author Xingbz
 * @createDate 2018-6-9
 */
@Controller
@RequestMapping("/manager/oloan")
public class OperatorLoanController extends BaseController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private DictService dictService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private MMemberService memberService;

    @GetMapping()
    @RequiresPermissions("manager:oloan:oloan")
    public String Loan(Integer type, Model model) {
        model.addAttribute("type", type);
        Map<String, Object> param = new HashMap<>();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.DICT_TYPE_CITYS);
        model.addAttribute("cityList", dictService.list(param));

        List<DictDO> statusList = new ArrayList<>();
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_SLCG), Loan.LOAN_STATUS_SLCG + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_DBCZL), Loan.LOAN_STATUS_DBCZL + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_ZLBCZ), Loan.LOAN_STATUS_ZLBCZ + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FWJXZ), Loan.LOAN_STATUS_FWJXZ + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FKCG), Loan.LOAN_STATUS_FKCG + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_SLSB), Loan.LOAN_STATUS_SLSB + ""));
        statusList.add(new DictDO(Loan.getDescByOrderSatatus(Loan.LOAN_STATUS_FKSB), Loan.LOAN_STATUS_FKSB + ""));
        model.addAttribute("statusList", statusList);


        if (Objects.equals(2, type)) { //已处理 , 查询运营岗
            List<UserDO> userList = new ArrayList<>();
            Map<String, Object> userParam = new HashMap<>();
            userParam.put("code", SysDictConstants.OPERATOR);
            UserDO uSerDO = ShiroUtils.getUser();
            if (null != uSerDO && StringUtils.isNotBlank(uSerDO.getCity())) {
                userParam.put("city", uSerDO.getCity());
            }

            List<UserDO> resultList = userService.getUserByRoleCode(userParam);
            if (null != resultList && !resultList.isEmpty()) {
                resultList.forEach(entity -> {
                    UserDO userDO = new UserDO();
                    userDO.setUserId(entity.getUserId());
                    userDO.setName(entity.getName());
                    userList.add(userDO);
                });
            }
            model.addAttribute("userList", userList);
        }

        return "manager/oloan/oloan";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("manager:oloan:oloan")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        /* 当前查询类型 */
        String typeStr = params.get("type") + "";
        if (StringUtils.isNotBlank(typeStr) && !"null".equals(typeStr) && !"undefined".equals(typeStr)) {
            Integer type = Integer.valueOf(typeStr);//查询类型

            /* 当前查询角色 */
            UserDO user = ShiroUtils.getUser();
            if (null != user && StringUtils.isNotBlank(user.getCity())) {//只看同属城市的
                params.put("city", user.getCity());
            }

            if (type == 1) {//1 未处理 状态为受理中
                params.put("orderStatus", Loan.LOAN_STATUS_SLZ);
            } else if (type == 2) { //2 已处理 状态未 受理成功/受理失败/待补充材料/资料补充中/服务进行中/放款成功/放款失败
                List<Integer> orderStatusList = new ArrayList<>();
                orderStatusList.add(Loan.LOAN_STATUS_SLCG);
                orderStatusList.add(Loan.LOAN_STATUS_DBCZL);
                orderStatusList.add(Loan.LOAN_STATUS_ZLBCZ);
                orderStatusList.add(Loan.LOAN_STATUS_FWJXZ);
                orderStatusList.add(Loan.LOAN_STATUS_FKCG);
                orderStatusList.add(Loan.LOAN_STATUS_SLSB);
                orderStatusList.add(Loan.LOAN_STATUS_FKSB);

                params.put("orderStatusList", orderStatusList);


                //已处理订单 , 运营岗只能看自己处理的
                List<Long> roleIds = user.getRoleIds();//当前用户拥有的角色
                if (null == roleIds || roleIds.isEmpty()) {
                    roleIds = userRoleDao.listRoleId(user.getUserId());
                }
                if (!containsSuper(roleIds) && containsOperator(roleIds)) {//不包含超级管理员且包含运营岗
                    params.put("column3", user.getUserId());
                }
            }
        }


        /* 时间处理 */
        String commitTime = getParamString(params, "commitTime");
        if (StringUtils.isNotBlank(commitTime) && commitTime.contains("~")) {//2018-06-20~2018-07-26
            String[] timeArr = commitTime.split("~");
            if (timeArr.length > 0) {
                String timeBeginStr = timeArr[0].trim();
                String timeBeginEnd = timeArr[1].trim();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Long timeBeginLong = LocalDateTime.of(LocalDate.parse(timeBeginStr, dtf), LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
                Long timeEndLong = LocalDateTime.of(LocalDate.parse(timeBeginEnd, dtf), LocalTime.MIN).plusDays(1).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
                params.put("timeLow", timeBeginLong);
                params.put("timeUp", timeEndLong);
            }
        }

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
            //区域管理员用户 已处理状态 数据需要脱敏
            loanList.forEach(entity -> {
                entity.setColumn1(StringUtils.isBlank(entity.getColumn1()) ? "000000" : entity.getColumn1());
                if ((isSuper() == 1 && StringUtils.isNotBlank(ShiroUtils.getUser().getCity())
                        || "2".equals(typeStr))) {
                    entity.setName(DataUtil.starsName(entity.getName()));
                    entity.setIdcard(DataUtil.starsIdcard(entity.getIdcard()));
                    entity.setLoanMemberPhone(DataUtil.starsPhone(entity.getLoanMemberPhone()));
                }
            });

        }
        int total = loanService.count(query);
        return new PageUtil(loanList, total);
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("manager:oloan:oloan")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("isSuper", isSuper());

        UserDO userDO = ShiroUtils.getUser();
        List<UserDO> userList = new ArrayList<>();
        Map<String, Object> userParam = new HashMap<>();
        if (null != userDO && StringUtils.isNotBlank(userDO.getCity())) {//只看同属城市的
            userParam.put("city", userDO.getCity());
        }
        userParam.put("code", SysDictConstants.COMMERCE);

        List<UserDO> resultList = userService.getUserByRoleCode(userParam);
        if (null != resultList && !resultList.isEmpty()) {
            resultList.forEach(entity -> {
                UserDO udo = new UserDO();
                udo.setUserId(entity.getUserId());
                udo.setName(entity.getName());
                userList.add(udo);
            });
        }
        model.addAttribute("userList", userList);


        model.addAttribute("loginUserName", userDO.getName());
        Loan loan = loanService.get(id);
//        //商务手机号
//        MMember member = memberService.get(loan.getPartner());
//        if (null != member) {
//            loan.setLoanMemberPhone(member.getPhone());
//        }

        if (!Objects.equals(loan.getOrderStatus(), Loan.LOAN_STATUS_SLZ)) {//已处理 , 数据脱敏
            loan.setName(DataUtil.starsName(loan.getName()));
            loan.setIdcard(DataUtil.starsIdcard(loan.getIdcard()));
            loan.setLoanMemberPhone(DataUtil.starsPhone(loan.getLoanMemberPhone()));
        }
        model.addAttribute("loan", loan);
        return "manager/oloan/edit";
    }


    /**
     * 处理
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("manager:oloan:oloan")
    public R update(Loan loan) {
        if (isSuper() == CommonConstants.YES) {//管理员角色不能处理
            return R.error("管理员不能介入操作");
        }

        if (null != loan) {
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

            loan.setOperatorTime(Instant.now().toEpochMilli());//运营处理时间
            loan.setColumn2(Instant.now().toEpochMilli() + "");//最近更新时间

            UserDO userDO = ShiroUtils.getUser();
            List<Long> roleIds = userDO.getRoleIds();//当前用户拥有的角色
            if (null == roleIds || roleIds.isEmpty()) {
                roleIds = userRoleDao.listRoleId(userDO.getUserId());
            }
            if (!containsSuper(roleIds) && containsOperator(roleIds)) {//运营角色操作有效
                loan.setColumn3(userDO.getUserId() + "");
                loan.setOperator(userDO.getName());
            }

            loanService.update(loan);
        }

        return R.ok();
    }

    @GetMapping("/showImg")
    @RequiresPermissions("manager:oloan:oloan")
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
}