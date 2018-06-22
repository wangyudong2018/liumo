package com.yiyun.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    CoreServer启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
                "   頂頂頂頂頂頂頂頂頂　頂頂頂頂頂頂頂頂頂\n" +
                "   頂頂頂頂頂頂頂　　　　　頂頂　　　　　\n" +
                "   　　　頂頂　　　頂頂頂頂頂頂頂頂頂頂頂\n" +
                "   　　　頂頂　　　頂頂頂頂頂頂頂頂頂頂頂\n" +
                "   　　　頂頂　　　頂頂　　　　　　　頂頂\n" +
                "   　　　頂頂　　　頂頂　　頂頂頂　　頂頂\n" +
                "   　　　頂頂　　　頂頂　　頂頂頂　　頂頂\n" +
                "   　　　頂頂　　　頂頂　　頂頂頂　　頂頂\n" +
                "   　　　頂頂　　　頂頂　　頂頂頂　　頂頂\n" +
                "   　　　頂頂　　　　　　　頂頂頂　\n" +
                "   　　　頂頂　　　　　　頂頂　頂頂　頂頂\n" +
                "   　頂頂頂頂　　　頂頂頂頂頂　頂頂頂頂頂\n" +
                "   　頂頂頂頂　　　頂頂頂頂　　　頂頂頂頂");
    }
}
