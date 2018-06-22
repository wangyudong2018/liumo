package com.yiyun.app.common.utils;

import com.yiyun.app.rabbitMQ.sender.Sender;
import com.yiyun.constants.rabbitMq.QueueName;
import net.sf.json.JSONObject;
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
public class SendMessageUtil {

    @Autowired
    private Sender sender;

    private static SendMessageUtil sendMessageUtil;

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
        push_message.put("loanSn", loanSn);
        push_message.put("memberId", memberId);
        push_message.put("title", title);
        push_message.put("content", content);
        json_push_message.putAll(push_message);
        sendMessageUtil.sender.send(QueueName.PUSH_MESSAGE_SINGLE, json_push_message.toString());
    }

    /*
     * 发送短信方法
     */
    public static void sendShortMessage(Integer type,String phone, String period, String merchantName, String className,
                                        String orderSn, String errorMessages, String amount) {
        HashMap<String, String> short_message = new HashMap<String, String>();
        JSONObject json_short_message = new JSONObject();

        short_message.put("type", type.toString());
        short_message.put("phone", phone);
        if (period != null) {
            short_message.put("period", period);
        }
        if (merchantName != null) {
            short_message.put("merchantName", merchantName);
        }
        if (className != null) {
            short_message.put("className", className);
        }
        if (orderSn != null) {
            short_message.put("orderSn", orderSn);
        }
        if (errorMessages != null) {
            short_message.put("errorMessages", errorMessages);
        }
        if (amount != null) {
            short_message.put("amount", amount);
        }
        json_short_message.putAll(short_message);
        sendMessageUtil.sender.send(QueueName.SHORT_MESSAGE, json_short_message.toString());

    }

}
