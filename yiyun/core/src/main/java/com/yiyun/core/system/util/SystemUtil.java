package com.yiyun.core.system.util;

import com.yiyun.core.common.config.SystemConfigurationProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class SystemUtil {

	@Autowired
	private static SystemConfigurationProperties sysConfigurationProperties;

	public static SystemConfigurationProperties getSysConfigurationProperties() {
		return sysConfigurationProperties;
	}

	@Autowired(required = true)
	public void setSystemConfigurationProperties(SystemConfigurationProperties sysConfigurationProperties) {
		SystemUtil.sysConfigurationProperties = sysConfigurationProperties;
	}
}
