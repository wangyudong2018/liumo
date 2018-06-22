package com.yiyun.constants;

/**
 * @title 系统中的一些常量
 * @description
 * @author Xingbz
 * @createDate 2017/12/18
 * @version 1.0
 */
public class SystemConstants {

    /** 角色标识 - 超级管理员 */
    public static final String ROLE_CODE_SUPER = "ALL";
    /** 角色标识 - 商务/业务员 */
    public static final String ROLE_CODE_SALESMAN = "SALESMAN";

    /**  数据字典默认的版本记录id*/
    public static final Long DICT_VERSION_ID = 0L;
    /** 默认的数据字典版本号 */
    public static final String DICT_DEFAULT_VERSION = "100";

    /**  字典标识 app*/
    public static final int DICT_FLAG_APP = 10;
    /** 字典标识 通用(默认) */
    public static final int DICT_FLAG_COMMON = 20;
    /** 字典标识 core */
    public static final int DICT_FLAG_WEB= 30;
}
