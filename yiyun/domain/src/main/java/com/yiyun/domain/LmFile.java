package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @title 六漠文件表
 * @author wangyudong
 * @date Thu Jun 28 23:09:06 CST 2018
 */
@Data
public class LmFile implements Serializable {
	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 类型
	private String lmType;
	// 文件
	private byte[] lmFile;
	// 排序
	private Integer sort;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

}
