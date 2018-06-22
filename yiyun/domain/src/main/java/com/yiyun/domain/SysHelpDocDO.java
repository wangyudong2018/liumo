package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;



/**
 * 帮助中心
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date Sat Dec 16 09:53:49 CST 2017
 */
@Data
public class SysHelpDocDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	@Id
	private Long id;
	//类型
	private String type;
	//类型描述
	private String description;
	//标题
	private String title;
	//内容
	private String body;
	//图标
	private String icon;
	//排序编号
	private Integer orderNum;
	//创建人
	private String creator;
	//创建时间
	private Long createTime;


}
