package com.yiyun.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Title 系统配置工具类
 * @Description 系统配置工具类
 * @author liliang
 * @createDate 2016年4月8日 下午5:24:27
 * @modifier
 * @modifyDate
 * @version 1.0
 */
public class SystemPropertiesUtil {
	
	private static SystemPropertiesUtil instance;
	private Properties sysProperties = new Properties();
	
	public synchronized static SystemPropertiesUtil getInstance(){
		if(instance == null){
			instance = new SystemPropertiesUtil();
		}
		return instance;
	}
	private SystemPropertiesUtil(){
		try {
			loadDefaults();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadDefaults() throws IOException {
		InputStream stream = null;
		try {
			stream = SystemPropertiesUtil.class.getClassLoader().getResourceAsStream("config/system.properties");
			sysProperties.load(stream);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
				}
				stream = null;
			}
		}
	}
	
	public Properties getSystemProperties() {
		return sysProperties;
	}
	
	public synchronized void setBusinessSystemProperty(String key, String value) throws IOException {
		sysProperties.setProperty(key, value);
	}
}
