package com.yiyun.app.system.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author YanXiaoGuang
 * @version 1.0
 * @title 广告分享返回信息
 * @description
 * @createDate 2017/12/20
 */
@Data
@ApiModel(value = "广告分享")
public class SysAdShareVO {
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String summary;
    /**
     * 内容
     */
    private String content;
    /**
     * 通用图片
     */
    private String commonImg;
    /**
     * 微信图片
     */
    private String wxImg;
    /**
     * ios图片
     */
    private String iosImg;
    /**
     * andriod图片
     */
    private String androidImg;
    /**
     * 链接
     */
    private String link;
    /**
     * 打开方式
     */
    private Integer openType;
    /**
     * 类别
     */
    private Integer category;
    /**
     * 排序号
     */
    private Integer sortNo;
    /**
     * 系统类别
     */
    private String sysType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 备注
     */
    private String remark;




}

