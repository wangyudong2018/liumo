package com.yiyun.app.common.annotation;


import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @Title JSON参数注解
 * @Description 标识SpringMVC接口中的参数类型为JSON
 * @author liliang
 * @createDate 2016年4月5日 下午5:07:09
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSONParam {
	String value();
}
