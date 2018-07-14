package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @title 六漠短信流水表
 * @author wangyudong
 * @date Sat Jul 14 13:40:23 CST 2018
 */
@Data
public class LmSmsLog implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 用户编号
	private String userId;
	// 手机号
	private String mobile;
	// 短信类型
	private String smsType;
	// 短信内容
	private String content;
	// 发送日期
	private Date sendDate;
	// 创建时间
	private Date createTime;

}
