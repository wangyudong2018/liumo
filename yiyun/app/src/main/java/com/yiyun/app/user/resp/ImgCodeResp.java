package com.yiyun.app.user.resp;

import java.io.Serializable;

/**
 * 
 * 
 * @Title 获取图片验证码返回参数
 * @Description
 * @author XieLinGe
 * @createDate 2017年7月20日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ImgCodeResp implements Serializable {
	//是否发送短信 0展示验证码 1发送短信验证码
	private String isSendMessage;
	//图形验证码
	private String imgCode;

	public String getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(String isSendMessage) {
		this.isSendMessage = isSendMessage;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
}
