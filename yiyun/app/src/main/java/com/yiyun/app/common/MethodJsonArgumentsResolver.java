package com.yiyun.app.common;

import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.utils.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;

/**
 * @Title 自定义SpringMVC JSON参数解析
 * @Description 自定义SpringMVC JSON参数解析
 * @author liliang
 * @createDate 2016年4月5日 下午5:02:11
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@Component
public class MethodJsonArgumentsResolver implements HandlerMethodArgumentResolver {


    /**
     * @Description 自动将JSON格式的参数转为bean
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     * @author liliang
     * @createDate 2016年4月5日 下午5:53:02
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        Object obj = BeanUtils.instantiate(parameter.getParameterType());

        //通过反射获取到注解的value,该value为参数名
        JSONParam annotation = parameter.getParameterAnnotation(JSONParam.class);
        Method m = annotation.getClass().getDeclaredMethod("value");
        String paramName = (String) m.invoke(annotation, new Object[]{});
        String paramValue = webRequest.getParameter(paramName);

        obj = JSONUtil.json2Bean(paramValue, parameter.getParameterType());
        return obj;
    }

    /**
     * @Description 判断参数是否为有JSONParam注解
     * @param parameter
     * @return
     * @author liliang
     * @createDate 2016年4月5日 下午5:03:19
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JSONParam.class);
    }

}
