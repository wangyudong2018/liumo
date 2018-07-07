package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @title 六漠新闻媒体发布
 * @author WangYuDong
 * @date Sat Jul 07 12:07:07 CST 2018
 */
@Data
public class LmRelease implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 发布类型（01新闻发布；02媒体报道）
	private String category;
	// 缩略图ID
	private Long thumbnail;
	// 发布标题
	private String title;
	// 发布简介
	private String brief;
	// 原创者
	private String original;
	// 是否置顶（1是；0否）
	private String stick;
	// 是否有效（1是；0否）
	private String state;
	// 外部链接
	private String outChain;
	// 新闻内容
	private String content;
	// 发布终端（00全部；01PC；02MB）
	private String terminal;
	// 排序
	private Integer sort;
	// 发布日期
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date releaseDate;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

}
