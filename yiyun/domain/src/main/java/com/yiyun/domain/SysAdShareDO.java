package com.yiyun.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 广告分享表
 * 
 * @author YaN
 * @email 1992lcg@163.com
 * @date Tue Dec 19 13:50:41 CST 2017
 */
@Data
@NoArgsConstructor
public class SysAdShareDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	@ApiModelProperty(value = "主键")
	private Long id;
	//标题
	@ApiModelProperty(value = "标题")
	private String title;
	//简介
	@ApiModelProperty(value = "简介")
	private String summary;
	//内容
	@ApiModelProperty(value = "内容")
	private String content;
	//通用图片
	@ApiModelProperty(value = "通用图片")
	private String commonImg;
	//微信图片
	@ApiModelProperty(value = "微信图片")
	private String wxImg;
	//IOS图片
	@ApiModelProperty(value = "IOS图片")
	private String iosImg;
	//安卓图片
	@ApiModelProperty(value = "安卓图片")
	private String androidImg;
	//链接
	@ApiModelProperty(value = "链接")
	private String link;
	//打开方式
	@ApiModelProperty(value = "打开方式")
	private Integer openType;
	//类别
	@ApiModelProperty(value = "类别")
	private Integer category;
	//排序
	@ApiModelProperty(value = "排序")
	private Integer sortNo;
	//系统类型
	@ApiModelProperty(value = "系统类型")
	private Integer sysType;
	//状态
	@ApiModelProperty(value = "状态")
	private Integer status;
	//广告客户
	@ApiModelProperty(value = "广告客户")
	private String advertiser;
	//广告起始时间
	@ApiModelProperty(value = "广告起始时间")
	private Long startTime;
	//广告结束时间
	@ApiModelProperty(value = "广告结束时间")
	private Long endTime;
	//广告金额
	@ApiModelProperty(value = "广告金额")
	private BigDecimal adAmount;
	//创建者
	@ApiModelProperty(value = "创建者")
	private String creator;
	//创建时间
	@ApiModelProperty(value = "创建时间")
	private Long createTime;
	//备注
	@ApiModelProperty(value = "备注")
	private String remark;

}
