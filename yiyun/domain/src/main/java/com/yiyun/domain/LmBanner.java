package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @title 六漠banner表
 * @author WangYuDong
 * @date Thu Jul 05 22:19:09 CST 2018
 */
@Data
public class LmBanner implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 渠道
	private String channel;
	// 属于哪个页面的banner
	private String page;
	// 文件表ID
	private String fileId;
	// 排序
	private Integer sort;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

}
