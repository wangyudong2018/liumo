package com.yiyun.app.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短信配置
 * 
 * @author XieLinGe
 *
 */
@ConfigurationProperties(prefix = "sms")
public class SmsConfigurationProperties {
	// 短信开关
	private boolean smsSwitch;

	public boolean isSmsSwitch() {
		return smsSwitch;
	}

	public void setSmsSwitch(boolean smsSwitch) {
		this.smsSwitch = smsSwitch;
	}

	@Override
	public String toString() {
		return "SmsConfigurationProperties{" +
				"smsSwitch=" + smsSwitch +
				'}';
	}
}
