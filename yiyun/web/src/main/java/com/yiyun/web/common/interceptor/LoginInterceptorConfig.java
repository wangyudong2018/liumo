package com.yiyun.web.common.interceptor;

import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
@EnableWebMvc
public class LoginInterceptorConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource(name = "redisTemplateDAO")
    private RedisTemplateDAO redisTemplateDAO;

    public LoginInterceptorConfig() {
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
        registry.addResourceHandler("/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/css/");
        registry.addResourceHandler("/docs/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/docs/");
        registry.addResourceHandler("/fonts/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/fonts/");
        registry.addResourceHandler("/img/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/img/");
        registry.addResourceHandler("/js/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/js/");
        registry.addResourceHandler("/plugins/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/plugins/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/templates/");

        super.addResourceHandlers(registry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则 , 只拦截微信的
        registry.addInterceptor(new LoginInterceptor(redisTemplateDAO)).addPathPatterns("/wechat/**");
        super.addInterceptors(registry);
    }
}
