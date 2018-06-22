package com.yiyun.app.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author liliang
 * @version 1.0
 * @Title APP v1.0接口公共参数
 * @Description APP v1.0接口公共参数
 * @createDate 2016年3月24日 下午4:32:30
 * @modifier Xingbz
 * @modifyDate 2017年8月18日
 */
@Data
@ToString
public class CommonParamReq {

    /**
     * 会员编号
     */
    @ApiModelProperty(value = "用户ID", hidden = true)
    private String memberId;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "用户手机号", hidden = true)
    private String phone;

    /**
     * 渠道号
     */
    @ApiModelProperty(hidden = true)
    private String chlId;

    /**
     * 设备号
     */
    @ApiModelProperty(hidden = true)
    private String deviceNO;

    /**
     * 客户端类型
     */
    @ApiModelProperty(hidden = true)
    private String cltType;

    /**
     * imsi
     */
    @ApiModelProperty(hidden = true)
    private String imsi;

    /**
     * imei
     */
    @ApiModelProperty(hidden = true)
    private String imei;

    /**
     * 手机型号
     */
    @ApiModelProperty(hidden = true)
    private String phoneModel;

    /**
     * 手机品牌
     */
    @ApiModelProperty(hidden = true)
    private String phoneBrand;

    /**
     * 系统版本
     */
    @ApiModelProperty(hidden = true)
    private String osVersion;

    /**
     * 手机mac地址
     */
    @ApiModelProperty(hidden = true)
    private String macAdd;

    /**
     * CPU型号
     */
    @ApiModelProperty(hidden = true)
    private String cpuModel;

    /**
     * CPU频率
     */
    @ApiModelProperty(hidden = true)
    private String cpuFrequency;

    /**
     * 时间戳
     */
    @ApiModelProperty(hidden = true)
    private String timestamp;

    /**
     * 签名
     */
    @ApiModelProperty(hidden = true)
    private String sign;

    /**
     * 版本
     */
    @ApiModelProperty(hidden = true)
    private String version;

    /**
     * 令牌
     */
    @ApiModelProperty(hidden = true)
    private String token;

}
