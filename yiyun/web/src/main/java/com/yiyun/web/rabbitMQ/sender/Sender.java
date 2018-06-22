package com.yiyun.web.rabbitMQ.sender;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	@Resource(name = "coreServerRabbitTemplate")
	private RabbitTemplate coreServerRabbitTemplate;

	public void send(String queueName, String content) {
		this.coreServerRabbitTemplate.convertAndSend(queueName, content);
	}

}
