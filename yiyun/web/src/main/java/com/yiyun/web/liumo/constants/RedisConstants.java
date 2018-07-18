package com.yiyun.web.liumo.constants;

import java.util.Random;

/**
 * @title RedisConstants
 * @author wangyudong
 * @date 2018年7月18日下午7:53:19
 */
public final class RedisConstants {

	/** 项目名称 */
	public static final String PROJECT_NAME = "liumo";
	/** 项目名称前缀 */
	public static final String PROJECT_NAME_PRE = PROJECT_NAME + "_";
	/** token的缓存key */
	public static final String CACHE_KEY_TOKEN = PROJECT_NAME_PRE + "token_";
	/** 短信验证码 */
	public static final String CACHE_KEY_SMS_CODE = PROJECT_NAME_PRE + "sms_code_";

	/**
	 * @param outTime
	 *            需要设置的标准时间
	 * @return 加上随机值的时间
	 * @title 获取需要设置的缓存过期时间 , 在原来标准时间上上进行0-20分钟随机延迟 , 防止同一时间大量缓存失效
	 */
	public static Long getCacheOutTime(Long outTime) {
		int randTime = new Random().nextInt(20);
		return outTime + randTime;
	}

}
