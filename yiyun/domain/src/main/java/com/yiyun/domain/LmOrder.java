package com.yiyun.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @title 六漠订单表
 * @author wangyudong
 * @date Mon Jul 16 23:06:06 CST 2018
 */
@Data
public class LmOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	// 订单编号
	private String id;
	// 用户编号
	private String userId;
	// 手机号
	private String mobile;
	// 借款人姓名
	private String username;
	// 证件类型
	private String certType;
	// 证件号码
	private String certNo;
	// 订单类型
	private String orderType;
	// 订单金额
	private BigDecimal orderAmt;
	// 状态01在线咨询02门店申请03匹配最优银行04银行审批放款05已放款06失败
	private String state;
	// 备注
	private String remark;
	// 状态更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderDate01;
	// 状态更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderDate02;
	// 状态更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderDate03;
	// 状态更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderDate04;
	// 状态更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderDate05;
	// 状态更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderDate06;
	// 创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	// 操作员更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date oprUpdateTime;
	// 最后更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

}
