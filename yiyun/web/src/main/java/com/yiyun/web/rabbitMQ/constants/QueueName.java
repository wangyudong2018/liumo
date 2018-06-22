package com.yiyun.web.rabbitMQ.constants;

/**
 * @Title 消息队列名
 * @Description 消息队列名
 * @author YanXiaoGuang
 * @createDate 2017年8月9日 下午4:01:48
 * @modifier
 * @modifyDate	
 * @version 1.0
 */
public class QueueName {
	
	/* 消息群发 */
	public static final String PUSH_MESSAGE_MASS = "PUSH_MESSAGE_MASS";
	/* 消息个推 */
	public static final String PUSH_MESSAGE_SINGLE = "PUSH_MESSAGE_SINGLE";
	/* 短信 */
	public static final String SHORT_MESSAGE = "SHORT_MESSAGE";
	/** 审核 */
	public static final String AUDIT_LOAN = "AUDIT_LOAN";
	/**s审核取消*/
	public static final String ORDER_SYSTEM_CANCEL ="ORDER_SYSTEM_CANCEL";

	/* 放款 */
	public static final String RELEASE_LOAN = "RELEASE_LOAN";
	/** 扣款 */
	public static final String DEDUCT_LOAN = "DEDUCT_LOAN";
	
	/* 还款 */
	public static final String REPAYMENT_LOAN = "REPAYMENT_LOAN";
	/* 单笔还款 */
	public static final String REPAYMENT_SINGLE_LOAN ="REPAYMENT_SINGLE_LOAN";
	/* 风控信息 */
	public static final String RISK_INFO = "RISK_INFO";
	/* 天机风控信息 */
	public static final String TIANJI_RISK_INFO = "TIANJI_RISK_INFO";

	/** 核心服务 - 易宝支付批扣通知 */
	public static final String YEEPAY_BATCH = "YEEPAY_BATCH";
}
