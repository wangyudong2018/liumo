package com.yiyun.core.rabbitMQ.config;

import com.yiyun.constants.rabbitMq.MqFactoryConstants;
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

    private static final String CONNECTION_FACTORY = "bbwlCoreServerConnectionFactory";
    private static final String TEMPLATE = "bbwlCoreServerRabbitTemplate";

    @Bean(name = CONNECTION_FACTORY)
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

    @Bean(name = TEMPLATE)
    @Primary
    public RabbitTemplate coreRabbitTemplate(
            @Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean(name = MqFactoryConstants.BBWL_CORE_FACTORY)
    public SimpleRabbitListenerContainerFactory firstFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                             @Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
