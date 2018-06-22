package com.yiyun.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @title
 * @author Xing
 * @date Tue Jun 05 07:37:41 CST 2018
 */
@Data
public class Adress implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //地址分类
    private String type;
    //所在城市
    private String city;
    //所在区域
    private String area;
    //联系电话
    private String phone;
    //详细地址
    private String detailAdress;
    //地址状态1可用0禁用
    private Integer status;
    //创建时间
    private Long createTime;
    //最近更新时间
    private Long updateTime;

    public static Object getTypeIdCode(String addrType) {
        if (Objects.equals(addrType, "1")) {
            return "公证处";
        }

        if (Objects.equals(addrType, "2")) {
            return "建委";
        }
        return null;
    }

    public static Object getCodeByType(String addrType) {
        if (Objects.equals(addrType, "公证处")) {
            return "1";
        }

        if (Objects.equals(addrType, "建委")) {
            return "2";
        }
        return null;
    }
}
