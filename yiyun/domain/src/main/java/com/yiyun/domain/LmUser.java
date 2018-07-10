package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @title 六漠用户信息表
 * @author WangYuDong
 * @date Tue Jul 10 20:45:56 CST 2018
 */
@Data
public class LmUser implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private String id;
	// 手机号
	private String mobile;
	// 姓名
	private String username;
	// 密码
	private String password;
	// 证件类型
	private String certType;
	// 证件号码
	private String certNo;
	// 实名认证标志（1是0否）
	private String certSign;
	// 实名认证审核评语
	private String certRemark;
	// 证件正面ID
	private String certPositive;
	// 证件反面ID
	private String certReverse;
	// 手持证件照ID
	private String certHand;
	// 实名认证通过时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date certDate;
	// 创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	// 用户更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date usrUpdateTime;
	// 操作员更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date oprUpdateTime;
	// 最后更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

}
