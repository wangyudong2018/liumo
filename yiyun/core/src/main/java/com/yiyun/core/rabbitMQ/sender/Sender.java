package com.yiyun.core.rabbitMQ.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Sender {
    @Resource(name = "bbwlCoreServerRabbitTemplate")
    private RabbitTemplate coreServerRabbitTemplate;

    public void send(String queueName, String content) {
        this.coreServerRabbitTemplate.convertAndSend(queueName, content);
    }

}
