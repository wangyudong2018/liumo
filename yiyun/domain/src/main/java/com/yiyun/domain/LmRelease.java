package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @title 六漠新闻媒体发布
 * @author wangyudong
 * @date Sun Jun 24 10:27:30 CST 2018
 */
@Data
public class LmRelease implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 种类
	private String category;
	// 发布简介
	private String brief;
	// 发布标题
	private String title;
	// 发布内容
	private String content;
	// logo
	private byte[] logo;
	// 排序
	private Integer sort;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

}
