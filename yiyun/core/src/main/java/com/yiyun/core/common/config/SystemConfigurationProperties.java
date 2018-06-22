package com.yiyun.core.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统通用的参数设置
 * 
 * @author XieLinGe
 *
 */
@ConfigurationProperties(prefix = "system")
public class SystemConfigurationProperties {
	private boolean master;

	private String mainServer;

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public String getMainServer() {
		return mainServer;
	}

	public void setMainServer(String mainServer) {
		this.mainServer = mainServer;
	}

	@Override
	public String toString() {
		return "SystemConfigurationProperties [master=" + master + ", mainServer=" + mainServer + "]";
	}

}