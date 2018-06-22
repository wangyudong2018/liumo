package com.yiyun.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @title
 * @author Xing
 * @date Fri Jun 08 10:55:33 CST 2018
 */
@Data
public class NewProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //产品名称
    private String name;
    //产品大类
    private String type1;
    //产品小类
    private String type2;
    //产品介绍
    private String content;
    //优势
    private String advantage;
    //产品城市 (前端area)
    private String inferiority;
    //资金方点位
    private String position;
    //状态1有效 , 2失效
    private Integer status;
    //创建时间
    private Long createTime;
    //更新时间
    private Long updateTime;
}
