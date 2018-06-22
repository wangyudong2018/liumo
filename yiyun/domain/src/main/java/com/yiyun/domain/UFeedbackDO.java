package com.yiyun.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;


/**
 * 用户反馈表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-12-13 16:02:47
 */
@Data
public class UFeedbackDO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	//用户ID
	@ApiModelProperty(value = "用户ID",required = true)
	@Id
	private Long memberId;
	//姓名
	@ApiModelProperty(value = "姓名",required = true)
	private String name;
	//反馈类别
	@ApiModelProperty(value = "反馈类别",required = true)
	private Integer type;
	//手机号
	@ApiModelProperty(value = "手机号",required = true)
	private String phone;
	//用户反馈信息
	@ApiModelProperty(value = "用户反馈信息",required = true)
	private String message;
	//处理意见
	@ApiModelProperty(value = "处理意见",required = false)
	private String advice;
	//手机型号
	@ApiModelProperty(value = "手机型号",required = true)
	private String phoneModel;
	//操作系统
	@ApiModelProperty(value = "操作系统",required = true)
	private String operatingSystem;
	//手机串码
	@ApiModelProperty(value = "手机串码",required = true)
	private String phoneImei;
	//处理状态
	@ApiModelProperty(value = "处理状态",required = false)
	private Integer status;
	//操作人
	@ApiModelProperty(value = "操作人",required = false)
	private String operator;
	//创建时间
	@ApiModelProperty(value = "创建时间",required = false)
	private Long createTime;
	//处理时间
	@ApiModelProperty(value = "处理时间",required = false)
	private Long dealTime;

	public UFeedbackDO() {
	}

	public UFeedbackDO(String memberId, String phone, String type, String message, String phoneModel, String operatingSystem, String phoneImei) {
		this.phone = phone;
		this.type=Integer.parseInt(type);
		this.memberId=Long.parseLong(memberId);
		this.message = message;
		this.phoneModel = phoneModel;
		this.operatingSystem = operatingSystem;
		this.phoneImei = phoneImei;
		this.status=0;//未处理
		this.createTime = System.currentTimeMillis();
	}

}
