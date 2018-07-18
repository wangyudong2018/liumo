package com.yiyun.web.liumo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @title SpringContent
 * @author wangyudong
 * @date 2018年7月18日下午10:02:09
 */
@Component
public class SpringContent implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public final static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}

}
