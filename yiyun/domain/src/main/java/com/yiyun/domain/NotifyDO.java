package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;



/**
 * 通知通告
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-05 17:11:16
 */
@Data
public class NotifyDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	@Id
	private Long id;
	//类型
	private String type;
	//标题
	private String title;
	//内容
	private String content;
	//附件
	private String files;
	//状态
	private String status;
	//创建者
	private Long createBy;
	//创建时间
	private Date createDate;
	//更新者
	private String updateBy;
	//更新时间
	private Date updateDate;
	//备注信息
	private String remarks;
	//删除标记
	private String delFlag;
	@Transient
	private Long[] userIds;
}
