package com.yiyun.web;

import com.yiyun.web.common.config.SmsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
@EnableConfigurationProperties(value = {SmsConfigurationProperties.class})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    WebServer启动成功      ヾ(◍°∇°◍)ﾉﾞ");
    }

}
