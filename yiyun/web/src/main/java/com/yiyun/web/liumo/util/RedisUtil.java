package com.yiyun.web.liumo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.alibaba.fastjson.JSON;
import com.yiyun.domain.LmUser;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import com.yiyun.web.liumo.constants.RedisConstants;

/**
 * @title RedisUtil
 * @author wangyudong
 * @date 2018年7月13日下午11:55:24
 */
public class RedisUtil {

	/**
	 * 从redis中获取数据
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static String get(String key) {
		RedisTemplateDAO redis = SpringContent.getBean(RedisTemplateDAO.class);
		String text = redis.get(key);
		return text;
	}

	/**
	 * 向redis中保存数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void save(String key, String value, Long time) {
		RedisTemplateDAO redis = SpringContent.getBean(RedisTemplateDAO.class);
		redis.put(key, value, time);
	}

	/**
	 * 向redis中delete数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void delete(String key) {
		RedisTemplateDAO redis = SpringContent.getBean(RedisTemplateDAO.class);
		redis.delete(key);
	}

	/**
	 * 查询登录用户信息
	 * 
	 * @return
	 */
	public static LmUser getLmUser(String token) {
		String text = get(RedisConstants.CACHE_KEY_TOKEN + token);
		if (StringUtils.isBlank(text)) {
			return null;
		}
		return JSON.parseObject(text, LmUser.class);
	}

	/**
	 * 存储登录用户信息
	 * 
	 * @param token
	 * @param lmUser
	 */
	public static void saveLmUser(String token, LmUser lmUser) {
		String obj = JSON.toJSONString(lmUser);
		save(RedisConstants.CACHE_KEY_TOKEN + token, obj, DateUtils.MILLIS_PER_DAY * 3);
	}

	/**
	 * 验证短信验证码是否正确
	 * 
	 * @param mobile
	 * @param type
	 * @param code
	 * @return
	 */
	public static boolean verifyCode(String mobile, String type, String code) {

		String key = RedisConstants.CACHE_KEY_SMS_CODE + mobile + "_" + type;
		String cacheCode = get(key);
		if (StringUtils.isBlank(cacheCode)) {
			return false;
		}

		delete(key);

		if (!StringUtils.equals(cacheCode, code)) {
			return false;
		}
		return true;
	}

	/**
	 * 存储短信验证码
	 * 
	 * @param mobile
	 * @param type
	 * @param code
	 */
	public static void saveCode(String mobile, String type, String code) {
		String key = RedisConstants.CACHE_KEY_SMS_CODE + mobile + "_" + type;
		save(key, code, DateUtils.MILLIS_PER_MINUTE * 15);
	}

}
