package com.yiyun.web.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    private SystemConfig config;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + config.getUploadPath());
        /**
         * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
         *  <!--swagger资源配置-->
         *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
         *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
         * @param registry
         */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}