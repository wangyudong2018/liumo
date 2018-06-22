package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;



/**
 * 系统通用参数
 * 
 * @author xx
 * @email xxx@163.com
 * @date Fri Dec 15 12:47:48 CST 2017
 */
@Data
@Table(name = "sys_common")
public class SysCommonDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	@Id
	private Long id;
	//会员编号
	private Long memberId;
	//手机号
	private String phone;
	//渠道号
	private String chlId;
	//设备号
	private String deviceNo;
	//客户端类型
	private String cltType;
	//imsi
	private String imsi;
	//imei
	private String imei;
	//手机型号
	private String phoneModel;
	//手机品牌
	private String phoneBrand;
	//系统版本
	private String osVersion;
	//手机mac地址
	private String macAdd;
	//CPU型号
	private String cpuModel;
	//CPU频率
	private String cpuFrequency;
	//客户端发送时间戳
	private String timestamp;
	//签名
	private String sign;
	//
	private String version;
	//令牌
	private String token;
	//创建时间
	private Long createTime;


}
