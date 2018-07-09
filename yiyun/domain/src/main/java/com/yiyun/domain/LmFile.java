package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @title 六漠文件表
 * @author WangYuDong
 * @date Mon Jul 09 19:45:18 CST 2018
 */
@Data
public class LmFile implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private String id;
	// 类型
	private String lmType;
	// 文件
	private byte[] lmFile;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

}
