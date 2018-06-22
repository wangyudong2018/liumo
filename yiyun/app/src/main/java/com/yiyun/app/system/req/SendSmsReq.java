package com.yiyun.app.system.req;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @Title 发送手机短信
 * @Description
 * @author XieLinGe
 * @createDate 2017年7月24日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class SendSmsReq {

	// 用户id
	@ApiModelProperty(value = "用户ID", required = true)
	private String userId;

	// 手机号码
	@ApiModelProperty(value = "手机号", required = true)
	private String phone;

	// 类型 0注册
	@ApiModelProperty(value = "类型 0注册登录", required = true)
	private String type;

	@ApiModelProperty(value = "验证码", required = true)
	private String code;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@NotBlank(message = "请输入类型")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotBlank(message = "请输入图片验证码")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotBlank(message = "请输入手机号")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "SendSmsReq [userId=" + userId + ", phone=" + phone + ", type=" + type + ", code=" + code + "]";
	}

}
