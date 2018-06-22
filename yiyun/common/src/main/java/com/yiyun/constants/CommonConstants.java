package com.yiyun.constants;

/**
 * @author Xingbz
 * @version 1.0
 * @title 与业务无关的通用常量
 * @description
 * @createDate 2017/12/15
 */
public class CommonConstants {

    /**
     * 是,确定,正确,成功,通过
     */
    public static final Integer YES = 1;
    /**
     * 否,取消,错误,失败,未通过
     */
    public static final Integer NO = 0;


    /** 自动操作人 */
    public static final String OPERATOR_SYSTEM = "SYSTEM";
    /** 操作人 : 用户 */
    public static final String OPERATOR_USER = "USER";

    /** 项目名称 */
    public static final String PROJECT_NAME = "yiyun";
    /** 项目名称前缀 */
    public static final String PROJECT_NAME_PRE = PROJECT_NAME + "_";

    /** 获客来源 */
    public static final String SOURCE_WEB = "网站";
    public static final String SOURCE_WECHAT = "公众号";
    public static final String SOURCE_MAPP = "小程序";


    /** session key */
    public static final String SKEY_CURRENT_MEMBER_ID = "current_member_id";
    public static final String SKEY_CURRENT_MEMBER_PHONE = "current_member_phone";
    public static final String SKEY_CURRENT_MEMBER = "current_member";


}
