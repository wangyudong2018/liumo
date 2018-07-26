package com.yiyun.web.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

	// 上传路径
	private String uploadPath;
	// 对外发布路径
	private String externalPath;

}
