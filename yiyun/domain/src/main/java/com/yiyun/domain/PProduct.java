package com.yiyun.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @title 产品
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Data
public class PProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //类别
    private Integer type;
    //状态
    private Integer productStatus;
    //产品名
    private String name;
    //图片
    private String logo;
    //介绍
    private String introduce;
    //月利下限
    private BigDecimal monthRateLower;
    //月利上限
    private BigDecimal monthRateUpper;
    //放款时间
    private Integer loanTime;
    //贷款期限下限
    private Integer loanLimitLower;
    //贷款期限上限
    private Integer loanLimitUpper;
    //放款金额下限
    private BigDecimal loanAmountLower;
    //放款金额上限
    private BigDecimal loanAmountUpper;
    //使用身份
    private String client;
    //还款方式
    private String repayMethod;
    //房产二押
    private Integer isMortgageAgain;
    //提前还款
    private Integer isEarlyRepay;
    //一次性服务费
    private String serverceCharge;
    //房龄要求
    private String houseTimeLimit;
    //房产估值要求
    private String houseValueLimit;
    //房产坐落区域
    private String houseArea;
    //抵押物类型
    private String pawnType;
    //经营时间
    private Integer operatorTime;
    //所需材料
    private String needData;
    //创建时间
    private Long createTime;
    //预留字段1
    private String column1;
    //预留字段2
    private String column2;
    //预留字段3
    private String column3;
}
