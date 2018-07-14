package com.yiyun.web.liumo.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.yiyun.domain.LmUser;
import com.yiyun.web.common.utils.HttpContextUtils;

/**
 * @title SessionUtil
 * @author wangyudong
 * @date 2018年7月13日下午11:55:24
 */
public class SessionUtil {

	public static final String LM_USER = "LM_USER";
	public static final String LM_CODE_MAP = "LM_CODE_MAP";
	public static final String LM_UPDATE_PWD = "LM_UPDATE_PWD";
	public static final String LM_UPDATE_MB = "LM_UPDATE_MB";

	/**
	 * 从session中获取数据
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T get(String key, Class<T> clazz) {
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		HttpSession session = request.getSession(false);
		if (null == session) {
			return null;
		}
		return clazz.cast(session.getAttribute(key));
	}

	/**
	 * 向Session中保存数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void save(String key, Object value) {
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}

	/**
	 * 查询登录用户信息
	 * 
	 * @return
	 */
	public static LmUser getLmUser() {
		return get(LM_USER, LmUser.class);
	}

	/**
	 * 存储登录用户信息
	 * 
	 * @param lmUser
	 */
	public static void saveLmUser(LmUser lmUser) {
		save(LM_USER, lmUser);
	}

	/**
	 * 验证短信验证码是否正确
	 * 
	 * @param mobile
	 * @param type
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean verifyCode(String mobile, String type, String code) {

		Map<String, String> lmCodeMap = get(LM_CODE_MAP, Map.class);
		if (CollectionUtils.isEmpty(lmCodeMap)) {
			return false;
		}

		String key = mobile + "_" + type;
		if (!StringUtils.equals(lmCodeMap.get(key), code)) {
			lmCodeMap.remove(key);
			return false;
		}

		lmCodeMap.remove(key);

		save(LM_CODE_MAP, lmCodeMap);

		return true;
	}

	/**
	 * 存储短信验证码
	 * 
	 * @param mobile
	 * @param type
	 * @param code
	 */
	@SuppressWarnings("unchecked")
	public static void saveCode(String mobile, String type, String code) {

		Map<String, String> lmCodeMap = get(LM_CODE_MAP, Map.class);
		if (CollectionUtils.isEmpty(lmCodeMap)) {
			lmCodeMap = new HashMap<String, String>(8);
		}

		String key = mobile + "_" + type;
		lmCodeMap.put(key, code);

		save(LM_CODE_MAP, lmCodeMap);
	}

}
