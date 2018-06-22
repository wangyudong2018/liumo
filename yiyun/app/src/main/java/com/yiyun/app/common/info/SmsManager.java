package com.yiyun.app.common.info;

import com.alibaba.fastjson.JSON;
import com.yiyun.constants.MessageConstant;
import com.yiyun.constants.MessageConstants;
import com.yiyun.constants.SmsConstants;
import com.yiyun.req.SmsSendReq;
import com.yiyun.resp.SendMessageResp;
import com.yiyun.resp.SmsSendResp;
import com.yiyun.utils.sms.ChuangLanSmsUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 *
 * @Title 短信管理类
 * @Description
 * @author XieLinGe
 * @createDate 2017年8月14日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class SmsManager {

    static final Logger logger = LogManager.getLogger(SmsManager.class.getName());

    /**
     * @Title 发送短信
     * @Description
     * @author XieLinGe
     * @createDate 2017年8月14日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static SendMessageResp sendMessage(String phone, String content) {
        SendMessageResp resp = new SendMessageResp();
        resp.setFlag(false);
        try {
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod(SmsConstants.BW_SENDMSGURL);
            post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
            NameValuePair[] data = {new NameValuePair("corp_id", SmsConstants.BW_ID),
                    new NameValuePair("corp_pwd", SmsConstants.BW_PASSWORD),
                    new NameValuePair("corp_service", SmsConstants.BW_SERVICE_CODE),
                    new NameValuePair("mobile", phone), new NameValuePair("msg_content", content),
                    new NameValuePair("corp_msg_id ", ""), new NameValuePair("ext ", "")};
            post.setRequestBody(data);
            client.executeMethod(post);
            int statusCode = post.getStatusCode();
            if (statusCode == 200) {
                String result = post.getResponseBodyAsString();
                /*
                 * 如果发送失败调用创蓝253发送
				 */
                logger.info("百悟发送短信返回结果：" + result);
                post.releaseConnection();
                if (result.substring(0, 1).equals("0")) {
                    // 返回通道
                    resp.setAlleywayName(MessageConstant.MESSAGE_BWTD);
                    resp.setFlag(true);
                    resp.setReturnResult(result);
                } else {
                    resp = sendMessageTwo(phone, content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("百悟发送短信出现异常：", e);
        }
        return resp;
    }

    public static String getMesageMoney() {
        String resultMoney = null;
        try {
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod("http://sms.hbsmservice.com:8080/get_balance.do");
            post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
            NameValuePair[] data = {new NameValuePair("id", SmsConstants.BW_ID),
                    new NameValuePair("pwd", SmsConstants.BW_PASSWORD)};
            post.setRequestBody(data);
            client.executeMethod(post);
            int statusCode = post.getStatusCode();
            logger.info("短信查询余额返回结果：" + statusCode);
            if (statusCode == 200) {
                String result = new String(post.getResponseBodyAsString());
                logger.info("短信查询余额：" + result);
                post.releaseConnection();
                if (StringUtils.isNotBlank(result)) {
                    resultMoney = result.substring(result.indexOf("#") + 1, result.length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMoney;
    }

    /**
     * @Title 253发送短信
     * @Description
     * @author XieLinGe
     * @createDate 2017年8月14日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static SendMessageResp sendMessageTwo(String phone, String content) {
        SendMessageResp resp = new SendMessageResp();
        resp.setFlag(false);
        try {
            // 请求地址
            String smsSingleRequestServerUrl = SmsConstants.CL_URL;
            // 状态报告
            String report = "true";
            SmsSendReq smsSingleRequest = new SmsSendReq(SmsConstants.CL_ACCOUNT,
                    SmsConstants.CL_PWD, content, phone, report);
            /**
             * 读取结果
             */
            String requestJson = JSON.toJSONString(smsSingleRequest);
            String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
            SmsSendResp smsSingleResponse = JSON.parseObject(response, SmsSendResp.class);
            logger.info("253发送短信返回结果：" + response);
            if ("0".equals(smsSingleResponse.getCode())) {
                // 返回通道
                resp.setAlleywayName(MessageConstant.MESSAGE_CLTD);
                resp.setFlag(true);
                resp.setReturnResult(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("253发送短信出现异常：", e);
        }
        return resp;
    }

    public static SendMessageResp sendMessageTwoTest(String phone, String content) {
        SendMessageResp resp = new SendMessageResp();
        resp.setFlag(false);
        try {
            // 请求地址
            String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
            // 状态报告
            String report = "true";
            SmsSendReq smsSingleRequest = new SmsSendReq("N1630743",
                    "1mDZVtFo9bf0b1", content, phone, report);
            /**
             * 读取结果
             */
            String requestJson = JSON.toJSONString(smsSingleRequest);
            String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
            SmsSendResp smsSingleResponse = JSON.parseObject(response, SmsSendResp.class);
            System.out.println("253发送短信返回结果：" + response);
            if ("0".equals(smsSingleResponse.getCode())) {
                // 返回通道
                resp.setAlleywayName(MessageConstant.MESSAGE_CLTD);
                resp.setFlag(true);
                resp.setReturnResult(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("253发送短信出现异常：");
        }
        return resp;
    }

    public static void main(String[] args) {
        String content = MessageConstants.MESSAGE_FSYZM.replace("A1", "2356");
        System.out.println(sendMessage("18612805739" , content));
    }
}
