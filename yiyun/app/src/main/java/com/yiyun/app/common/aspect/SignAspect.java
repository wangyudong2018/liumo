package com.yiyun.app.common.aspect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.yiyun.exceptions.AuthenticationException;
import com.yiyun.utils.ASignUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @Title 签名验证切面
 * @Description 验证签名是否正确
 * @author liliang
 * @createDate 2016年3月25日 上午11:32:26
 * @modifier
 * @modifyDate
 * @version 1.0s
 */
@Aspect
@Component
@Order(2)
public class SignAspect {
	private static final Logger logger = LogManager.getLogger(SignAspect.class.getName());

	/**
	 * @Description controller日志切入点
	 * @author liliang
	 * @createDate 2016年3月25日 下午1:15:46
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 */
	@Pointcut("execution(* com.yiyun.app.*.controller.*.*(..))")
	public void accessController() {
	}
	/** 忽略的URL */
	private static final String[] NO_CHECK_URL = new String[]{"callback","appSysVersion"};
	/**
	 * @Description controller访问前记录日志
	 * @param joinPoint
	 *            切入点
	 * @author liliang
	 * @createDate 2016年3月25日 下午1:16:16
	 * @modifier
	 * @modifyDate
	 * @version 1.0
	 * @throws Exception
	 */
	@Before("accessController()")
	public void doBeforeController(JoinPoint joinPoint) throws Exception {
		/**
		 *  判断只对POST请求做处理
		 *  接收到请求，记录请求内容
		 */
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String method = request.getMethod();
		String url = request.getRequestURL().toString();
		if ("POST".equals(method)) {
			  for (String str : NO_CHECK_URL) {//不拦截上面定义的路径
		            if (url.contains(str)) {
		            	return;
		            }
		        }
			try {
			  	//TODO 直接返回
			  	if(1==1){;
			  		return;
				}
				// 签名验证失败
				if (!verifySign(joinPoint.getArgs()[0])) {
					throw new AuthenticationException("签名错误");
				}
			} catch (AuthenticationException ate) {
				logger.error("签名验证失败");
				throw new AuthenticationException("签名错误");
			} catch (Exception e) {
				logger.error("controller访问前日志记录异常", e);
				throw new AuthenticationException("签名错误");
			}
		} else {
			//get请求不作处理
		}
	}

	private boolean verifySign(Object bean)
			throws IllegalArgumentException, IllegalAccessException, AuthenticationException {
		Map<String, String> paramMap = new HashMap<String, String>();
		Class clazz = bean.getClass();
		Field[] fs = clazz.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			field.setAccessible(true); // 设置些属性是可以访问的
			Object val = field.get(bean);// 得到此属性的值
			if (val != null) {
				if (field != null && val != null) {
					String k = field.getName();
					String v = val.toString();
					paramMap.put(k, v);
				}
			}
		}
		String sign = paramMap.get("sign");
		if (StringUtils.isBlank(sign)) {
			return false;
		}

		return ASignUtil.rsaCheck(paramMap, "UTF-8");
	}
}