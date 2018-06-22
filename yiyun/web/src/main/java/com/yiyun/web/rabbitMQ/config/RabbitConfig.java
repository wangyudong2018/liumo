package com.yiyun.web.rabbitMQ.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {

    @Bean(name = "coreServerConnectionFactory")
    @Primary
    public ConnectionFactory firstConnectionFactory(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean(name = "coreServerRabbitTemplate")
    @Primary
    public RabbitTemplate coreRabbitTemplate(
            @Qualifier("coreServerConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate coreServerRabbitTemplate = new RabbitTemplate(connectionFactory);
        return coreServerRabbitTemplate;
    }

    @Bean(name = "coreServerFactory")
    public SimpleRabbitListenerContainerFactory firstFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                             @Qualifier("coreServerConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
