package com.yiyun.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @title 用户反馈表
 * @author Xing
 * @date Fri Jun 08 13:13:45 CST 2018
 */
@Data
public class Newfeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //用户ID
    private Long memberId;
    //姓名
    private String name;
    //手机号
    private String phone;
    //客户类型 小程序/网站/公众号
    private String type;
    //用户反馈信息
    private String message;
    //处理意见
    private String advice;
    //处理状态 0 未处理 1已处理
    private Integer status;
    //操作人
    private String operator;
    //创建时间
    private Long createTime;
    //处理时间
    private Long dealTime;
    //订单号
    private Long loan;
    //商务ID
    private Long customerId;
    //商务名称
    private String customer;
}
