package com.yiyun.utils.sms;

import com.yiyun.constants.MessageConstants;

/**
 * @author XieLinGe
 * @version 1.0
 * @Title 根据数据加载短信模板
 * @Description
 * @createDate 2017年8月15日
 * @modifier
 * @modifyDate
 */
public class ContentUtils {
    /**
     * @Title 加载短信模板
     * @Description
     * @author XieLinGe
     * @createDate 2017年8月14日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public static String getMessageInfo(MessageReq req) {
        // 短信模板替换值
        String content = MessageConstants.getMessageContentByType(Integer.valueOf(req.getType()));
        // 将参数的数据替换到模板上
        if (req.getType().equals("4") || req.getType().equals("6") || req.getType().equals("7") || req.getType().equals("9")) {
            content = content.replace("A1", req.getPeriod());
        } else if (req.getType().equals("-1") || req.getType().equals("10") || req.getType().equals("11") || req.getType().equals("22") || req.getType().equals("23") || req.getType().equals("24")) {
            content = content.replace("A1", req.getOrderSn());
        } else if (req.getType().equals("12")) {
            content = content.replace("A1", req.getOrderSn()).replace("A2", req.getErrorMessages());
        } else if (req.getType().equals("8")) {
            content = content.replace("A1", req.getPeriod()).replace("A2", req.getResson());
        } else if (req.getType().equals("21")) {
            content = content.replace("A1", req.getOrderSn()).replace("A2", req.getAmount());
        } else if (req.getType().equals("2")) {
            content = content.replace("A1", req.getMerchantName());
        } else if (req.getType().equals("5")) {
            content = content.replace("A1", req.getOrderSn()).replace("A2", req.getResson());
        } else if (req.getType().equals("1") || req.getType().equals("3")) {
            content = content.replace("A1", req.getMerchantName()).replace("A2", req.getClassName());
        } else {
            content = "";
        }
        return content;
    }
}
