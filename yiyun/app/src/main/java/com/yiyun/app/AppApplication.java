package com.yiyun.app;

import com.yiyun.app.common.MethodJsonArgumentsResolver;
import com.yiyun.app.common.config.SmsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@EnableConfigurationProperties(value = {SmsConfigurationProperties.class})
public class AppApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    AppServer启动成功      ヾ(◍°∇°◍)ﾉﾞ");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new MethodJsonArgumentsResolver());    //添加自定义参数解析器
    }
}
