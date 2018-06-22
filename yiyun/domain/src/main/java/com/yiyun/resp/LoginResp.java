package com.yiyun.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @Title 登录成功返回参数
 * @Description
 * @author XieLinGe
 * @createDate 2017年7月20日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@Data
public class LoginResp implements Serializable {
	// 用户id
	private Long memberId;
	//是否绑定手机号
	private String isBindPhone;
	/** 校验令牌 */
	private String token;

    private String redirectUrl;
}
