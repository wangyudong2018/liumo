package com.yiyun.app.common.req;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @Title 用户通用的两个参数
 * @Description
 * @author Xingbz
 * @createDate 2017年7月21日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class UserCommonReq {

	/** 会员ID */
	@ApiModelProperty(value = "用户id", required = true)
	private String memberId;
	/** 手机号 */
	@ApiModelProperty(value = "手机号", required = true)
	private String phone;

	@NotBlank(message = "会员ID不能为空")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@NotBlank(message = "手机号不能为空")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "MemberCommonReq [memberId=" + memberId + ", phone=" + phone + "]";
	}

}
