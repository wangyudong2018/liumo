package com.yiyun.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @title 会员身份证信息
 * @author Xing
 * @date Fri Jun 08 16:25:09 CST 2018
 */
@Data
public class Idcard implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //会员编号
    private Long memberId;
    //姓名
    private String name;
    //身份证号
    private String idcard;
    //性别1男0女
    private Integer gender;
    //年龄
    private Integer age;
    //身份证地址
    private String adress;
    //创建时间
    private Long createTime;
}
