package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @title 六漠招聘
 * @author WangYuDong
 * @date Sun Jul 08 10:02:59 CST 2018
 */
@Data
public class LmRecruit implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 工作地点
	private String workplace;
	// 招聘类型（01社会招聘02校园招聘）
	private String recType;
	// 职位分类
	private String category;
	// 职位名称
	private String jobTitle;
	// 工作性质
	private String jobNature;
	// 薪资范围
	private String salaryRange;
	// 招聘人数
	private String hiring;
	// 工作职责
	private String jobDuty;
	// 任职资格
	private String jobQuality;
	// 是否有效（1有效0无效）
	private String state;
	// 发布时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date relDate;
	// 排序
	private Integer sort;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

}
