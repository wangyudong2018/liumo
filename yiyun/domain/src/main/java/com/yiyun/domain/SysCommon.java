package com.yiyun.domain;


import lombok.Data;

import javax.persistence.Id;

/**
 * 系统通用参数
 * 
 */
@Data
public class SysCommon implements java.io.Serializable {

	private static final long serialVersionUID = -7501851803744776121L;

	/** 主键 */
	@Id
	private Long id;

	/** 会员编号 */
	private Long memberId;

	/** 手机号 */
	private String phone;
	
	/** 渠道号 */
	private String chlId;

	/** 设备号 */
	private String deviceNo;

	/** 客户端类型 */
	private String cltType;

	/** imsi */
	private String imsi;
	
	/** imei */
	private String imei;

	/** 手机型号 */
	private String phoneModel;

	/** 手机品牌 */
	private String phoneBrand;

	/** 系统版本 */
	private String osVersion;

	/** 手机mac地址 */
	private String macAdd;

	/** CPU型号 */
	private String cpuModel;

	/** CPU频率 */
	private String cpuFrequency;
	
	/** 时间戳 */
	private String timestamp;

	/** 签名 */
	private String sign;

	/** 版本 */
	private String version;

	/** 令牌 */
	private String token;

	/** 创建时间 */
	private Long createTime;
}