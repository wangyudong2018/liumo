package com.yiyun.constants;

/**
 * @Title 用户常量类
 * @Description
 * @author XieLinGe
 * @createDate 2017年8月14日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class MemberConstants {

    /**
     * 登录方式 1 微信 0手机号 2.绑定手机号
     */
    public final static String LOGIN_PHONE = "0";
    public final static String LOGIN_WECHAT = "1";
    public final static String BIND_PHONE = "2";

    /** 用户类型 1合伙人 */
    public final static Integer M_TYPE_HHR = 1;

    /** 用户类型 2客户 */
    public final static Integer M_TYPE_KH = 2;

    /** 用户状态 1:正常 */
    public static final Integer RESERVE_STATUS_YGQ = 1;
    /** 用户状态 -1:黑名单*/
    public static final Integer RESERVE_STATUS_YHQX = -1;

}
