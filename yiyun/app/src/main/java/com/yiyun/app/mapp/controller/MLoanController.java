package com.yiyun.app.mapp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiyun.app.common.controller.MappBaseController;
import com.yiyun.app.mapp.service.MLoanService;
import com.yiyun.app.rabbitMQ.util.SendMqUtil;
import com.yiyun.constants.CommonConstants;
import com.yiyun.dao.cluster.ClusterLoanDao;
import com.yiyun.dao.cluster.ClusterMMemberDao;
import com.yiyun.dao.cluster.ClusterUserDao;
import com.yiyun.dao.master.LoanDao;
import com.yiyun.dao.master.MMemberDao;
import com.yiyun.dao.master.NewfeedbackDao;
import com.yiyun.domain.Loan;
import com.yiyun.domain.MMember;
import com.yiyun.domain.Newfeedback;
import com.yiyun.domain.UserDO;
import com.yiyun.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/**
 * @title 首页申请订单 / 订单查询
 * @author Xingbz
 * @createDate 2018-6-10
 */
@RestController
@RequestMapping("/appminiprog")
public class MLoanController extends MappBaseController {

    private static final Logger logger = LoggerFactory.getLogger(MLoanController.class);

    @Autowired
    private ClusterMMemberDao clusterMMemberDao;

    @Autowired
    private MMemberDao memberDao;

    @Autowired
    private MLoanService mLoanService;

    @Autowired
    private ClusterLoanDao clusterLoanDao;

    @Autowired
    private LoanDao loanDao;

    @Autowired
    private NewfeedbackDao newfeedbackDao;

    @Autowired
    private ClusterUserDao clusterUserDao;

    /**
     * 功能：合作申请
     * 输入：timestamp,v,miniid,partner_id,city,req_amount,room_photo
     * 输出：status,errorcode,messages,partner_id,order_id
     */
    @ResponseBody
    @RequestMapping(value = "/requestLoan", produces = "application/json; charset=utf-8")
    public String requestLoan(Long partner_id, String city, BigDecimal req_amount, String[] room_photo, HttpServletRequest request) {
        logger.info("XInfO.[合作申请]请求参数 : " + partner_id + " , " + " , " + city + req_amount + " , ");
        try {
            //检查参数是否合法
            if (null == partner_id || null == city || null == req_amount || null == room_photo || room_photo.length == 0) {
                return returnFailCode("05", "缺少参数");
            }

            //检查partnerId(memberId)是否存在
            MMember member = clusterMMemberDao.get(partner_id);
            if (null == member) {
                return returnFailCode("05", "参数错误partner_id对应的信息不存在");
            }

            /*
                1 . 查询合伙人信息
                2 . set订单合伙人的 邀请者码
                3 . 根据邀请码查询主要人员 , 更新成功邀请次数
             */

            Loan loan = new Loan();
            String orderSn = mLoanService.getOrderSn();
            loan.setOrderSn(orderSn);//订单号
            loan.setPartner(partner_id);
            loan.setLoanMemberPhone(member.getPhone());
            loan.setOrderStatus(Loan.LOAN_STATUS_SLZ);
            loan.setOrderStep(Loan.LOAN_STEP_YUNYING);
            loan.setLoanAmount(req_amount);
            loan.setCity(Loan.getCityByPinyin(city));
            loan.setSource(CommonConstants.SOURCE_MAPP);
            loan.setCreateTime(Instant.now().toEpochMilli());
            loan.setPhoto(JSON.toJSONString(room_photo));


            String investCode = member.getInvesterCode();//主邀请人邀请码
            if (!"000000".equals(investCode)) {
                loan.setColumn1(investCode);
                MMember investMember = getByInvestCode(loan.getColumn1());//主邀请人
                if (null != investMember) {
                    investMember.setInvestLoanCount(null == investMember.getInvestLoanCount() ? 1 : investMember.getInvestLoanCount() + 1);
                    memberDao.update(investMember);
                }
            }
            loanDao.save(loan);

            Map<String, Object> loanParam = new HashMap<>();
            loanParam.put("orderSn", orderSn);//保存之后反查 , 返回给前端
            List<Loan> loans = clusterLoanDao.list(loanParam);
            if (null != loans && !loans.isEmpty()) {
                Loan dbLoan = loans.get(0);
                if (null != dbLoan) {
                    Long loanId = dbLoan.getId();
                    Map<String, Object> data = new HashMap<>();
                    data.put("partner_id", partner_id);
                    data.put("order_id", loanId);
                    return returnData(data);
                }
            }

            return returnSucess("成功");
        } catch (Exception e) {
            logger.error("XErroR.[合作申请]异常", e);
            return returnFailCode("01", "提交合作申请时发生异常");
        }
    }

    private MMember getByInvestCode(String column1) {
        Map<String, Object> param = new HashMap<>();
        param.put("memberInvestCode", column1);
        List<MMember> memberList = clusterMMemberDao.list(param);
        if (null != memberList && !memberList.isEmpty()) {
            return memberList.get(0);
        }
        return null;
    }

    /**
     * 功能：订单查询
     * 输入：timestamp,v,miniid,partner_id
     * 输出：status,errorcode,messages,partner_id,orderlist,
     * orderlist内容：order_id,req_amount,loan_value,order_state
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrderList", produces = "application/json; charset=utf-8")
    public String queryOrderList(Long partner_id, HttpServletRequest request) {
        logger.info("XInfO.[订单查询]请求参数 : " + partner_id);
        if (null == partner_id) {
            return returnFailCode("05", "缺少参数,partner_id不存在");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("partner", partner_id);
        List<Loan> loans = clusterLoanDao.list(param);
        if (null != loans && !loans.isEmpty()) {
            List<JSONObject> result = new ArrayList<>();
            loans.forEach(entity -> {
                JSONObject loanJson = new JSONObject();
                loanJson.put("ORDER_ID", entity.getId());
                loanJson.put("REQ_AMOUNT", entity.getLoanAmount());
                loanJson.put("LOAN_VALUE", entity.getExpectAmount() == null ? "" : entity.getExpectAmount() + "");
                loanJson.put("ORDER_STATE", entity.getOrderStatusDesc());
                String customerMobile = "";
                if (StringUtils.isNotBlank(entity.getColumn4())) {
                    Map<String, Object> userParam = new HashMap<>();
                    userParam.put("userId", entity.getColumn4());
                    List<UserDO> userDOList = clusterUserDao.list(userParam);
                    if (null != userDOList && !userDOList.isEmpty()) {
                        UserDO userDO = userDOList.get(0);
                        if (null != userDO && StringUtils.isNotBlank(userDO.getMobile())) {
                            customerMobile = userDO.getMobile();
                        }
                    }
                }
                loanJson.put("OPERA_MOBILE", customerMobile);
                result.add(loanJson);
            });

            Map<String, Object> data = new HashMap<>();
            data.put("partner_id", partner_id);
            data.put("orderlist", result);
            return returnData(data);
        }
        logger.error("XErroR.[订单查询]未查到任何订单信息 . memberId : " + partner_id);
        return returnSucess();
    }

    /**
     * 功能：订单详情
     * 输入：timestamp,v,miniid,order_id
     * 输出：status,errorcode,messages，order_id，city，req_amount，create_time，loan_value，order_state，supplyinfo，otherinfo
     */
    @ResponseBody
    @RequestMapping(value = "/queryOrder", produces = "application/json; charset=utf-8")
    public Object queryOrder(Long order_id) {
        logger.info("XInfO.[订单详情]请求参数 : " + order_id);
        try {
            if (null == order_id) {
                return returnFailCode("05", "缺少参数,order_id不存在或为空");
            }
            Loan loan = clusterLoanDao.get(order_id);
            if (null == loan) {
                return returnFailCode("05", "order_id对应的订单不存在");
            }

            Map<String, Object> result = new JSONObject();
            result.put("order_id", loan.getId());
            result.put("req_amount", loan.getLoanAmount());
            result.put("loan_value", loan.getExpectAmount() == null ? "" : loan.getExpectAmount() + "");
            result.put("order_state", loan.getOrderStatusDesc());
            result.put("city", loan.getCity());
            result.put("create_time", DateUtil.long2String(loan.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            result.put("supplyinfo", loan.getRemark() == null ? "" : loan.getRemark());
            result.put("other", loan.getColumn5() == null ? "" : loan.getColumn5());

            String customerMobile = "";
            if (StringUtils.isNotBlank(loan.getColumn4())) {
                Map<String, Object> userParam = new HashMap<>();
                userParam.put("userId", loan.getColumn4());
                List<UserDO> userDOList = clusterUserDao.list(userParam);
                if (null != userDOList && !userDOList.isEmpty()) {
                    UserDO userDO = userDOList.get(0);
                    if (null != userDO && StringUtils.isNotBlank(userDO.getMobile())) {
                        customerMobile = userDO.getMobile();
                    }
                }
            }
            result.put("opera_mobile", customerMobile);

            /* 照片材料 */
            List<String> allImgList = new ArrayList<>();
            String photoArrStr = loan.getPhoto();
            if (StringUtils.isNotBlank(photoArrStr)) {
                photoArrStr = photoArrStr.replace("[", "").replace("]", "").replace("\\", "").replace("\"", "");
                if (StringUtils.isNotBlank(photoArrStr)) {
                    String[] photoArr = photoArrStr.split(",");
                    if (photoArr.length > 0) {
                        allImgList.addAll(Arrays.asList(photoArr));
                    }
                }
            }
            String otherDataArr = loan.getOtherData();
            if (StringUtils.isNotBlank(otherDataArr)) {
                otherDataArr = otherDataArr.replace("[", "").replace("]", "").replace("\\", "").replace("\"", "");
                if (StringUtils.isNotBlank(otherDataArr)) {
                    String[] photoArr = otherDataArr.split(",");
                    if (photoArr.length > 0) {
                        allImgList.addAll(Arrays.asList(photoArr));
                    }
                }
            }

            result.put("room_photo", allImgList);

            return returnData(result);
        } catch (Exception e) {
            logger.error("XErroR.[订单详情]异常 . orderId : " + order_id, e);
            return returnFailCode("01", "订单查询时发生异常");
        }
    }


    /**
     * 功能：补充资料
     * 输入：timestamp,v,miniid,order_id，room_photo
     * 输出：status,errorcode,messages，order_id
     */
    @ResponseBody
    @RequestMapping(value = "/supplyInfo", produces = "application/json; charset=utf-8")
    public Object supplyInfo(Long order_id, String[] room_photo) {
        logger.info("XInfO.[补充资料]请求参数 : " + order_id);
        try {
            //检查参数的正确性
            if (null == order_id || null == room_photo || room_photo.length == 0) {
                return returnFailCode("05", "缺少参数,order_id room_photo不存在或为空");
            }

            Loan dbLoan = clusterLoanDao.get(order_id);
            if (null == dbLoan) {
                return returnFailCode("05", "order_id对应的订单不存在");
            }

            if (!Loan.LOAN_STATUS_DBCZL.equals(dbLoan.getOrderStatus())) {//非待补充资料状态 , 不能补充资料
                return returnFailCode("05", "order_id对应的订单不能补充资料");
            }

            List<String> otherDataAll = new ArrayList<>();

            String oldOtherData = dbLoan.getOtherData();
            if (StringUtils.isNotBlank(oldOtherData)) {
                List<String> oldOtherDataList = JSON.parseArray(oldOtherData, String.class);
                if (null != oldOtherDataList && !oldOtherDataList.isEmpty()) {
                    otherDataAll.addAll(oldOtherDataList);
                }
            }

            List<String> newOtherDataList = Arrays.asList(room_photo);
            otherDataAll.addAll(newOtherDataList);

            dbLoan.setOtherData(JSON.toJSONString(otherDataAll));
            dbLoan.setOrderStatus(Loan.LOAN_STATUS_ZLBCZ);
            loanDao.update(dbLoan);

            //更新之后 , 短信通知
            MMember member = clusterMMemberDao.get(dbLoan.getPartner());
            if (null != member) {
                SendMqUtil.sendMessage1(member.getPhone(), member.getName());
            }

            Map<String, Object> result = new HashMap<>();
            result.put("order_id", order_id);
            return returnData(result);
        } catch (Exception e) {
            logger.error("XErroR.[补充资料]异常", e);
            return returnFailCode("01", "补充资料时发生异常");
        }
    }


    /**
     * 功能：评价
     */
    @ResponseBody
    @RequestMapping(value = "/commitFeedback", produces = "application/json; charset=utf-8")
    public Object feedback(Long order_id, String content, HttpSession session) {
        logger.info("XInfO.[评价反馈]请求参数 : " + order_id + " , " + content);
        try {
            //检查参数的正确性
            if (null == order_id || StringUtils.isBlank(content)) {
                return returnFailCode("05", "缺少参数,order_id content不存在或为空");
            }

            Loan dbLoan = clusterLoanDao.get(order_id);
            if (null == dbLoan) {
                return returnFailCode("05", "order_id对应的订单不存在");
            }

            String currentPhone = (String) session.getAttribute(CommonConstants.SKEY_CURRENT_MEMBER_PHONE);

            Newfeedback newfeedback = new Newfeedback();
            newfeedback.setMemberId(dbLoan.getPartner());//合伙人ID
            newfeedback.setPhone(currentPhone);
            newfeedback.setMessage(content);
            newfeedback.setStatus(0);
            newfeedback.setCreateTime(Instant.now().toEpochMilli());
            newfeedback.setLoan(order_id);
            String customerId = dbLoan.getColumn4();
            if (StringUtils.isNotBlank(customerId)) {
                newfeedback.setCustomerId(Long.valueOf(customerId));
            }
            newfeedback.setCustomer(dbLoan.getCustomer());

            newfeedbackDao.save(newfeedback);//保存反馈
            return returnSucess();
        } catch (Exception e) {
            logger.error("XErroR.[评价反馈]异常", e);
            return returnFailCode("01", "评价时发生异常");
        }
    }
}