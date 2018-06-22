package com.yiyun.web.common.config;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

@Configuration
@EnableSwagger2 // 启用 Swagger
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
       Predicate<RequestHandler> predicate = input -> {
           Class<?> declaringClass = input != null ? input.declaringClass() : null;
           if (declaringClass == BasicErrorController.class) return false;
           if (declaringClass != null && declaringClass.isAnnotationPresent(RestController.class)) return true;
           return input != null && input.isAnnotatedWith(ResponseBody.class);
       };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate::test)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("熠云Web接口")//大标题
                .version("1.0.0")//版本
                .build();
    }
}