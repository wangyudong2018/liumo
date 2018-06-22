package com.yiyun.web.rabbitMQ.util;

import com.alibaba.fastjson.JSONObject;
import com.yiyun.constants.MessageConstants;
import com.yiyun.constants.NewMessageConstatns;
import com.yiyun.constants.rabbitMq.QueueName;
import com.yiyun.resp.SendMessageResp;
import com.yiyun.web.common.info.SmsManager;
import com.yiyun.web.rabbitMQ.sender.Sender;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;


/**
 * @author XieLinGe
 * @version 1.0
 * @Title 发送消息短信工具类
 * @Description
 * @createDate 2017年8月21日
 * @modifier
 * @modifyDate
 */
@Component
public class SendMqUtil {

    private final static Logger logger = LogManager.getLogger(SendMqUtil.class.getName());

    @Autowired
    private Sender sender;

    private static SendMqUtil sendMessageUtil;

    public void setUserInfo(Sender sender) {
        this.sender = sender;
    }

    @PostConstruct
    public void init() {
        sendMessageUtil = this;
        sendMessageUtil.sender = this.sender;

    }

    /*
 * 消息推送方法
 */
    public static void sendPushMessage(String loanSn, String phone, String memberId, String title, String content) {
        HashMap<String, String> push_message = new HashMap<String, String>();
        JSONObject json_push_message = new JSONObject();
        push_message.put("phone", phone);
        push_message.put("memberId", memberId);
        push_message.put("loanSn", loanSn);
        push_message.put("title", title);
        push_message.put("content", content);
        json_push_message.putAll(push_message);
        logger.info("调用mq发送消息：" + json_push_message.toString());
        sendMessageUtil.sender.send(QueueName.PUSH_MESSAGE_SINGLE, json_push_message.toString());
    }


    /*
     * 定制消息推送
     */
//    public static void sendPushMessage(SysMessageDO sysMessageDO, SysMessagePushReq SysMessagePushReq) {
//        HashMap<String, String> push_message = new HashMap<String, String>();
//        JSONObject json_push_message = new JSONObject();
//        //消息参数
//        push_message.put("title", sysMessageDO.getTitle());
//        push_message.put("content", sysMessageDO.getContent());
//        push_message.put("commonImg", sysMessageDO.getCommonImg());
//        push_message.put("link", sysMessageDO.getLink());
//        //条件参数
//        push_message.put("sex", SysMessagePushReq.getSex());
//        push_message.put("channelType", SysMessagePushReq.getChannelType());
//        push_message.put("startTime", DateUtil.long2String(SysMessagePushReq.getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        push_message.put("endTime", DateUtil.long2String(SysMessagePushReq.getEndTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        json_push_message.putAll(push_message);
//        logger.info("调用mq消息个推：" + json_push_message.toString());
//        sendMessageUtil.sender.send(QueueName.PUSH_MESSAGE_SINGLE, json_push_message.toString());
//    }

    /*
     * 消息群发
     */
//    public static void sendPushMessageAll(SysMessageDO sysMessageDO) {
//        HashMap<String, String> push_message = new HashMap<String, String>();
//        JSONObject json_push_message = new JSONObject();
//        push_message.put("id", sysMessageDO.getId() + "");
//        push_message.put("title", sysMessageDO.getTitle());
//        push_message.put("content", sysMessageDO.getContent());
//        push_message.put("commonImg", sysMessageDO.getCommonImg());
//        push_message.put("link", sysMessageDO.getLink());
//        json_push_message.putAll(push_message);
//        logger.info("调用mq消息群发：" + json_push_message.toString());
//        sendMessageUtil.sender.send(QueueName.PUSH_MESSAGE_MASS, json_push_message.toString());
//    }

    /*
	 * 发送放款mq
	 */
//    public static void sendReleaseLoan(String loanSn, String userName) {
//        HashMap<String, String> loan_message = new HashMap<String, String>();
//        JSONObject json_loan_message = new JSONObject();
//        loan_message.put("loanSn", loanSn);
//        loan_message.put("userName", userName);
//        json_loan_message.putAll(loan_message);
//        logger.info("调用mq放款：" + json_loan_message.toString());
//        sendMessageUtil.sender.send(QueueName.MAKE_LOANS, json_loan_message.toString());
//    }
//
    /*
	 * 发送短信方法
	 */
    public static void sendShortMessage(String type, String phone, String money, String repayMoney, String bankNum,
                                        String repayTime) {
        HashMap<String, String> short_message = new HashMap<String, String>();
        JSONObject json_short_message = new JSONObject();
        short_message.put("type", type);
        short_message.put("phone", phone);
        short_message.put("money", money);
        if (repayMoney != null) {
            short_message.put("repayMoney", repayMoney);
        }
        if (bankNum != null) {
            short_message.put("bankNum", bankNum);
        }
        if (repayTime != null) {
            short_message.put("repayTime", repayTime);
        }
        json_short_message.putAll(short_message);
        logger.info("调用mq发送短信：" + json_short_message.toString());
        sendMessageUtil.sender.send(QueueName.SHORT_MESSAGE, json_short_message.toString());
    }
//
//	/*
//	 * 风控信息
//	 */
//	public static void sendRiskMessage(String memberId,String loanSn, String appType) {
//		HashMap<String, String> risk_message = new HashMap<String, String>();
//		JSONObject json_risk_message = new JSONObject();
//		risk_message.put("loanSn", loanSn);
//		risk_message.put("memberId", memberId);
//		risk_message.put("appType", appType);
//		json_risk_message.putAll(risk_message);
//		logger.info("调用mq风控信息：" + json_risk_message.toString());
//		sendMessageUtil.sender.send(QueueName.RISK_INFO, json_risk_message.toString());
//
//	}
//

    /**
     * 	发送单笔还款mq回调
     * @param serialNum
     * @param status
     * @param repayAmount
     * @param repayMethod
     * @param errorMsg
     */
    public static void sendRepaymentLoan(String serialNum, String status, String repayAmount, int repayMethod, String errorMsg) {
        HashMap<String, String> repayment_message = new HashMap<String, String>();
        JSONObject json_repayment_message = new JSONObject();
        repayment_message.put("serialNum", serialNum);
        repayment_message.put("status", status);
        repayment_message.put("repayAmount", repayAmount);
        repayment_message.put("repayMethod", repayMethod + "");
        repayment_message.put("errorMsg", errorMsg);
        json_repayment_message.putAll(repayment_message);
        logger.info("调用mq还款：" + json_repayment_message.toString());
        sendMessageUtil.sender.send(QueueName.REPAYMENT_SINGLE_LOAN, json_repayment_message.toString());
    }


    /**
     * 	发送批量还款mq回调
     * @param status
     */
    public static void sendYeepayBatchtLoan(String requestno, String ybbatchno, String status) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("requestno", requestno);
        jsonObj.put("ybbatchno", ybbatchno);
        jsonObj.put("status", status);

        logger.info("调用mq批量还款：" + jsonObj.toString());
        sendMessageUtil.sender.send(QueueName.YEEPAY_BATCH_NOTIFY, jsonObj.toString());
    }

    /*
	 * 发送代扣mq
	 * liyinq
	 */
public static void sendChargeBacks(String orderSn,String periodId) {
    HashMap<String, String> loan_message = new HashMap<String, String>();
    JSONObject json_loan_message = new JSONObject();
    loan_message.put("orderSn", orderSn);
    loan_message.put("periodId",periodId);
    json_loan_message.putAll(loan_message);
    logger.info("调用mq代扣款：" + json_loan_message.toString());
    sendMessageUtil.sender.send(QueueName.DEDUCT_LOAN, json_loan_message.toString());
}
	/*
	 * 发送订单取消确认mq
	 * liyinq
	 */
	public static void sendRemoveOrder(String remark,String orderSn) {
		HashMap<String, String> loan_message = new HashMap<String, String>();
		JSONObject json_loan_message = new JSONObject();
		loan_message.put("orderSn", orderSn);
		loan_message.put("remark", remark);
		json_loan_message.putAll(loan_message);
		logger.info("调用mq取消订单确认：" + json_loan_message.toString());
        sendMessageUtil.sender.send(QueueName.CANCEL_ORDER_APPLY, json_loan_message.toString());
    }
    /*
         * 取消订单后台审核mq（退款）
         * liyinq
         */
	public static void sendRefund(String remark,String orderSn,String userName,Long userId,Long operatorId,String operatorName,Integer status){
        HashMap<String, String> loan_message = new HashMap<String, String>();
        JSONObject json_loan_message = new JSONObject();
        loan_message.put("userName",userName);
        loan_message.put("userId",userId.toString());
        loan_message.put("orderSn", orderSn);
        loan_message.put("desc", remark);
        loan_message.put("operatorId", operatorId.toString());
        loan_message.put("operatorName", operatorName);
        loan_message.put("isRefund", status.toString());
        json_loan_message.putAll(loan_message);
        logger.info("调用mq退款：" + json_loan_message.toString());
        sendMessageUtil.sender.send(QueueName.CANCEL_ORDER_AUDIT, json_loan_message.toString());
    }

    /**
     * @title 商家确认消费端
     * @description
     * @author XieLinGe
     * @param
     * @return
     */
//    public static void sendMerchantConfirm(String orderSn,Boolean type,String resson,Long userId,String userName){
//        MqSellerConfirmVo req = new MqSellerConfirmVo();
//        req.setOrderSn(orderSn);
//        req.setResult(type);
//        req.setReason(resson);
//        req.setOperatorId(userId);
//        req.setOperatorName(userName);
//        sendMessageUtil.sender.send(QueueName.SELLER_CONFIRM, JSON.toJSONString(req));
//    }
    public static SendMessageResp sendShortMessageMine(String phone, String code) {
        String content = MessageConstants.MESSAGE_FSYZM.replace("A1", code);
        return SmsManager.sendMessage(phone, content);
    }

    /**
     * 补充资料发送短信
     * @param phone
     * @param name
     * @return
     */
    public static SendMessageResp sendMessage3(String phone, String name) {
        String content = NewMessageConstatns.MESSAGE3;
        if (StringUtils.isNotBlank(name)) {
            content = String.format(NewMessageConstatns.MESSAGE3, name);
        }
        return SmsManager.sendMessage(phone, content);
    }

}
