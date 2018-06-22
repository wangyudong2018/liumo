package com.yiyun.app.rabbitMQ.util;

import com.alibaba.fastjson.JSONObject;
import com.yiyun.app.common.info.SmsManager;
import com.yiyun.app.rabbitMQ.sender.Sender;
import com.yiyun.constants.MessageConstants;
import com.yiyun.constants.NewMessageConstatns;
import com.yiyun.constants.rabbitMq.QueueName;
import com.yiyun.resp.SendMessageResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;


/**
 * @Title 发送消息短信工具类
 * @Description
 * @author XieLinGe
 * @createDate 2017年8月21日
 * @modifier
 * @modifyDate
 * @version 1.0
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
     * 发送放款mq
     */
    public static void sendReleaseLoan(String loanSn, String userName) {
        HashMap<String, String> loan_message = new HashMap<String, String>();
        JSONObject json_loan_message = new JSONObject();
        loan_message.put("loanSn", loanSn);
        loan_message.put("userName", userName);
        json_loan_message.putAll(loan_message);
        logger.info("调用mq放款：" + json_loan_message.toString());
        sendMessageUtil.sender.send(QueueName.MAKE_LOANS, json_loan_message.toString());
    }

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

    /*
     * 风控信息
     */
    public static void sendRiskMessage(String userId, String OrderSn, String appType) {
        //调用风控
        JSONObject tempJson = new JSONObject();
        tempJson.put("userId", userId);
        tempJson.put("orderSn", OrderSn);
        tempJson.put("appType", ""); //应用类型暂时为空
        logger.info("调用mq风控信息：" + tempJson.toString());
        sendMessageUtil.sender.send(QueueName.RISK_INFO, tempJson.toString());
    }

    /**
     * 	发送单笔还款mq回调
     * @param serialNum
     * @param success
     * @param message
     * @param respNum
     * @param amount
     */
    public static void sendRepaymentLoan(Boolean success, String serialNum, String message, String respNum, String amount) {
        HashMap<String, String> repayment_message = new HashMap<String, String>();
        JSONObject json_repayment_message = new JSONObject();
        repayment_message.put("success", success.toString());
        repayment_message.put("serialNum", serialNum);
        repayment_message.put("message", message);
        repayment_message.put("respNum", respNum);
        repayment_message.put("amount", amount);
        json_repayment_message.putAll(repayment_message);
        logger.info("调用mq还款：" + json_repayment_message.toString());
        sendMessageUtil.sender.send(QueueName.REPAYMENT_SINGLE_LOAN, json_repayment_message.toString());
    }


    /**
     * 	发送批量还款mq回调
     */
    public static void sendYeepayBatchtLoan(String requestno, String ybbatchno, String status) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("requestno", requestno);
        jsonObj.put("ybbatchno", ybbatchno);
        jsonObj.put("status", status);

        logger.info("调用mq批量还款：" + jsonObj.toString());
        sendMessageUtil.sender.send(QueueName.YEEPAY_BATCH_NOTIFY, jsonObj.toString());
    }

    //========================================= 新 =========================
    public static SendMessageResp sendShortMessageMine(String phone, String code) {
        String content = MessageConstants.MESSAGE_FSYZM.replace("A1", code);
        return SmsManager.sendMessage(phone, content);
    }

    /**
     * 资料上传发送短信
     * @param phone
     * @param name
     * @return
     */
    public static SendMessageResp sendMessage1(String phone, String name) {
        String content = NewMessageConstatns.MESSAGE1;
        if (StringUtils.isNotBlank(name)) {
            content = String.format(NewMessageConstatns.MESSAGE2, name);
        }
        return SmsManager.sendMessage(phone, content);
    }
}
