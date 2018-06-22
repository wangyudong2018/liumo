package com.yiyun.utils.sms;

/**
 * @author XieLinGe
 * @version 1.0
 * @Title 短信参数接收类
 * @Description
 * @createDate 2017年8月14日
 * @modifier
 * @modifyDate
 */

public class MessageReq {

    //手机号
    private String phone;

    // 期次
    private String period;
    // 类型
    private String type;
    // 原因
    private String resson;

    // 商户名
    private String merchantName;

    // 课程名
    private String className;

    // 订单号
    private String orderSn;

    // 错误信息项
    private String errorMessages;

    // 金额
    private String amount;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    //    @NotBlank(message = "类别不能为空")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    //    @NotBlank(message = "手机号不为空")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResson() {
        return resson;
    }

    public void setResson(String resson) {
        this.resson = resson;
    }

    @Override
    public String toString() {
        return "MessageReq{" +
                "phone='" + phone + '\'' +
                ", period='" + period + '\'' +
                ", type='" + type + '\'' +
                ", resson='" + resson + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", className='" + className + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", errorMessages='" + errorMessages + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
