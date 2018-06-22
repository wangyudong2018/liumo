package com.yiyun.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xingbz
 * @title 多级字典条目信息
 * @date Tue Jan 09 18:33:26 CST 2018
 */
@Data
public class SysHierarchyDict implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //自身code , 不用ID
    private String selfCode;
    //类型1:省市县地址;2:商圈信息
    private Integer type;
    //等级.1:省;2:区/市;3:县/商圈(可无限累计)
    private Integer level;
    //名称
    private String name;
    //父级
    private String parentCode;
    //序号
    private Integer sort;
    //创建时间
    private Long createTime;
}
