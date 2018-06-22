package com.yiyun.core.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "yiyun", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
