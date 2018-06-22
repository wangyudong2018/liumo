package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;



/**
 *  系统参数
 * 
 * @author xbz
 * @email 1992lcg@163.com
 * @date Thu Dec 14 18:00:10 CST 2017
 */
@Data
public class SysParam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	@Id
	private Long id;
	//类型
	private String type;
	//类型描述
	private String description;
	//参数代码
	private String code;
	//参数名
	private String name;
	//参数值
	private String value;
	//参数说明
	private String remark;
	//启用状态
	private Integer status;
	//排序
	private BigDecimal sort;
	//操作人
	private String creator;
	//创建时间
	private Long createTime;
}
