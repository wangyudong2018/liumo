package com.yiyun.constants;

import java.util.Random;

import static com.yiyun.constants.CommonConstants.PROJECT_NAME_PRE;

/**
 * @title REDIS缓存需要的相关常量
 * @description
 * @author Xingbz
 * @createDate 2017/12/18
 * @version 1.0
 */
public class RedisConstants {


    /**  通用数据的缓存名称 , 系统参数 , 字典*/
    public static final String CACHE_NAME_COMMON = PROJECT_NAME_PRE + "common";
    /**  系统数据的缓存名称 , 角色列表*/
    public static final String CACHE_NAME_SYSTEM = PROJECT_NAME_PRE + "system";
    /**  字典的缓存名称 , 商圈信息*/
    public static final String CACHE_NAME_DICT = PROJECT_NAME_PRE + "dict";

    /**  字典的缓存key */
    public static final String CACHE_KEY_COMMON_DICT_ALL = PROJECT_NAME_PRE + "common_dict_all";
    /** 数据字典的版本号 */
    public static final String CACHE_KEY_COMMON_DICT_VERSION = PROJECT_NAME_PRE + "common_dict_version";
    /**  角色的缓存key */
    public static final String CACHE_KEY_SYS_ROLE_ALL = PROJECT_NAME_PRE + "sys_role_all";


    /**  系统参数的缓存key */
    public static final String CACHE_KEY_COMMON_PARAM = PROJECT_NAME_PRE + "common_param_";
    /** 分期类别 / 费率的缓存key */
    public static final String CACHE_KEY_ORDER_RATE = PROJECT_NAME_PRE + "order_rate_";

    /**  App帮助中心的缓存key */
    public static final String CACHE_KEY_APP_HELPS = PROJECT_NAME_PRE + "app_helps";

    /**  token的缓存key */
    public static final String CACHE_KEY_MEMBER_TOKEN = PROJECT_NAME_PRE + "member_token_";

    /** 短信验证码 */
    public static final String CACHE_KEY_SMS_CODE = PROJECT_NAME_PRE + "sms_code_";
    /** 短信验证码次数 */
    public static final String CACHE_KEY_SMS_CODE_COUNT = PROJECT_NAME_PRE + "sms_code_count_";

    /** 短信验证码 */
    public static final String CACHE_KEY_LOGIN_SMS_CODE = PROJECT_NAME_PRE + "login_sms_code_";
    /** 短信验证码次数 */
    public static final String CACHE_KEY_LOGIN_SMS_CODE_COUNT = PROJECT_NAME_PRE + "login_sms_code_count_";

    /** 超时时间 , 一天 */
    public static final Long OUT_TIME_ONE_DAY = 3600L;

    /** 超时时间 , 一周 */
    public static final Long OUT_TIME_ONE_WEEK = 3600L * 24 * 7;

    /** 超时时间 , 一个月 */
    public static final Long OUT_TIME_ONE_MONTH = 3600L * 24 * 30;

    /**
     * @param outTime 需要设置的标准时间
     * @return 加上随机值的时间
     * @title 获取需要设置的缓存过期时间 , 在原来标准时间上上进行0-20分钟随机延迟 , 防止同一时间大量缓存失效
     */
    public static Long getCacheOutTime(Long outTime) {
        int randTime = new Random().nextInt(20);
        return outTime + randTime;
    }

}
