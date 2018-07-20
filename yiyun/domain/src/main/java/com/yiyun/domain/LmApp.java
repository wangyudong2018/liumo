package com.yiyun.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @title 六漠APP表
 * @author wangyudong
 * @date Fri Jul 20 21:51:20 CST 2018
 */
@Data
public class LmApp implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private String id;
	// 版本号
	private String version;
	// 文件地址
	private String fileUrl;
	// 更新日志
	private String log;
	// 文件大小
	private Long size;
	// 文件的MD5
	private String md5;
	// 是否强制更新(1是0否)
	private String constraint;
	// 更新时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

}
