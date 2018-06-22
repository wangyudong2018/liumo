package com.yiyun.constants.rabbitMq;

/**
 *@title 消息队列常量
 *@description
 *@author Xingbz
 *@createDate 2018/1/16
 *@version 1.0
 */
public class QueueName {

    public static final String PROJECT_PRE = "YIYUN_";
    
    /** 取消订单-用户申请*/
    public static final String CANCEL_ORDER_APPLY = PROJECT_PRE + "CANCEL_ORDER_APPLY";

    /** 取消订单-后台审核*/
    public static final String CANCEL_ORDER_AUDIT = PROJECT_PRE + "CANCEL_ORDER_AUDIT";

    /** 取消订单-退款回调*/
    public static final String CANCEL_ORDER_REFUND = PROJECT_PRE + "CANCEL_ORDER_REFUND";

    /** 审核订单（待审核，审核失败，补充资料）*/
    public static final String AUDIT_ORDER = PROJECT_PRE + "AUDIT_ORDER";

    /** 定制消息推送*/
    public static final String PUSH_MESSAGE_SINGLE = PROJECT_PRE + "PUSH_MESSAGE_SINGLE";

    /** 消息群发*/
    public static final String PUSH_MESSAGE_MASS = PROJECT_PRE + "PUSH_MESSAGE_MASS";

    /* 短信 */
    public static final String SHORT_MESSAGE = PROJECT_PRE + "SHORT_MESSAGE";

    /* 风控信息 */
    public static final String RISK_INFO = PROJECT_PRE + "RISK_INFO";

    //====================================放款/扣款相关=============================
    /* 放款 */
    public static final String MAKE_LOANS = PROJECT_PRE + "MAKE_LOANS";

    /** 放款回调 */
    public static final String MAKE_LOANS_NOTIFY = PROJECT_PRE + "MAKE_LOANS_NOTIFY";

    /** 放款确认 */
    public static final String MAKE_LOANS_CONFIRM = PROJECT_PRE + "MAKE_LOANS_CONFIRM";

    /** 商户确认 */
    public static final String SELLER_CONFIRM = PROJECT_PRE + "SELLER_CONFIRM";

    /** 手动扣款 */
    public static final String DEDUCT_LOAN = PROJECT_PRE + "DEDUCT_LOAN";

    /* 单笔还款/扣款回调 */
    public static final String REPAYMENT_SINGLE_LOAN = PROJECT_PRE + "REPAYMENT_SINGLE_LOAN";


    /** 批扣回调 */
    public static final String YEEPAY_BATCH_NOTIFY = PROJECT_PRE + "YEEPAY_BATCH_NOTIFY";
}
