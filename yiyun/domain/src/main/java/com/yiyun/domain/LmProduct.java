package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @title 六漠产品介绍
 * @author wangyudong
 * @date Fri Jun 22 23:28:52 CST 2018
 */
@Data
public class LmProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 产品名称
	private String title;
	// 产品介绍
	private String content;
	// 居间服务
	private String agreemt;
	// 服务费率
	private String rate;
	// 适合人群
	private String people;
	// logo
	private String logo;
	// 排序
	private Integer sort;
	// 是否有效：1有效0无效
	private String state;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
}
