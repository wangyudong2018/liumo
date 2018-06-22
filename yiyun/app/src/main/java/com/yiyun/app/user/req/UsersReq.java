package com.yiyun.app.user.req;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @Title 用户登陆注册参数
 * @Description
 * @author XieLinGe
 * @createDate 2017年7月20日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class UsersReq {
	// 手机号
	@ApiModelProperty(value = "手机号", hidden = true,required = true)
	private String phone;

	// 来源
	@ApiModelProperty(value = "来源", hidden = true, required = true)
	private String source;

	// 短信验证码
	@ApiModelProperty(value = "短信验证码", hidden = true, required = true)
	private String smsCode;

	// 昵称
	@ApiModelProperty(value = "昵称微信传", hidden = true, required = true)
	private String nickName;

	// 头像
	@ApiModelProperty(value = "头像微信传", hidden = true, required = true)
	private String headImg;
	// 微信openId
	@ApiModelProperty(value = "openId微信传",  hidden = true,required = true)
	private String openId;

	//登录方式
	@ApiModelProperty(value = "登录方式 0手机号 1微信 2绑定手机号", hidden = true, required = true)
	private String loginType;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotBlank(message = "来源不能为空")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@NotBlank(message = "登录方式不能为空")
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "UsersReq{" +
				"phone='" + phone + '\'' +
				", source='" + source + '\'' +
				", smsCode='" + smsCode + '\'' +
				", nickName='" + nickName + '\'' +
				", headImg='" + headImg + '\'' +
				", openId='" + openId + '\'' +
				", loginType='" + loginType + '\'' +
				'}';
	}
}
