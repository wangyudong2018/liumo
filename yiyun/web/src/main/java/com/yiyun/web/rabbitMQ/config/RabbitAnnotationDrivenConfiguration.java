package com.yiyun.web.rabbitMQ.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConditionalOnClass(EnableRabbit.class)
class RabbitAnnotationDrivenConfiguration {

	@Autowired(required = false)
	private PlatformTransactionManager transactionManager;

	@Bean
	@ConditionalOnMissingBean(name = "rabbitListenerContainerFactory")
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
			ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		if (this.transactionManager != null) {
			factory.setTransactionManager(this.transactionManager);
		}
		return factory;
	}

	@EnableRabbit
	@ConditionalOnMissingBean(name = RabbitListenerConfigUtils.RABBIT_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)
	protected static class EnableRabbitConfiguration {

	}

}
