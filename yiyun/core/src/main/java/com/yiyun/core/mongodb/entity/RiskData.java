package com.yiyun.core.mongodb.entity;

import lombok.Data;

/**
 * @title
 * @author yxg
 * @date Thu Jan 11 18:46:50 CST 2018
 */
@Data
public class RiskData {

    //用户ID
    private Long userId;
    //启用状态
    private Integer status;
    //风控任务编号
    private String riskSn;
    //风控分数
    private String riskScore;
    //风控源数据
    private String riskData;
    //手机三要素任务编号
    private String phoneSn;
    //手机三要素认证结果
    private String phoneResult;
    //运营商任务编号
    private String mobileSn;
    //手机三要素时间
    private Long phoneTime;
    //运营商源数据
    private String mobileData;
    //运营商报告源数据
    private String mobileReportData;
    //运营商时间
    private Long mobileTime;
    //手机三要素源数据
    private String phoneData;
    //风控时间
    private Long riskTime;
    //银行任务编号
    private String bankSn;
    //银行结果
    private String bankResult;
    //银行源数据
    private String bankData;
    //银行时间
    private Long bankTime;
    //淘宝任务编号
    private String taobaoSn;
    //淘宝源数据
    private String taobaoData;
    //淘宝时间
    private Long taobaoTime;
    //京东任务编号
    private String jdSn;
    //京东源数据
    private String jdData;
    //京东时间
    private Long jdTime;
    //创建人
    private String creator;
    //创建时间
    private Long createTime;
    //预留字段1
    private String column1;
    //预留字段2
    private String column2;
    //预留字段3
    private String column3;
    //同盾风控返回数据
    private String riskItems;
    //同盾报告编号
    private String reportId;
    //同盾风控评分
    private Integer terracePoint;
}
