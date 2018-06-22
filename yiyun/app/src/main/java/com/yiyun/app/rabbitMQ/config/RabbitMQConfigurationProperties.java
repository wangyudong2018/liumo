package com.yiyun.app.rabbitMQ.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * rabbitMQ连接池配置
 * 
 * @author YanXiaoGuang
 *
 */
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQConfigurationProperties {

	private String address;
	private String port;
	private String username;
	private String password;
	private String virtualHost;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVirtualHost() {
		return virtualHost;
	}
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}
	@Override
	public String toString() {
		return "RabbitMQConfigurationProperties [address=" + address + ", port=" + port + ", username=" + username
				+ ", password=" + password + ", virtualHost=" + virtualHost + "]";
	}

}
