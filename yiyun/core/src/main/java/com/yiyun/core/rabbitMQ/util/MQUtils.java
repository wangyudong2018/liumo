package com.yiyun.core.rabbitMQ.util;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * RabbitMQ
 * Sender
 */
public class MQUtils {
	
	private static ConnectionFactory connectionFactory(){
		ConnectionFactory connectionFactory=new ConnectionFactory();
		connectionFactory.setHost("118.190.76.84");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("qjinfo");
        connectionFactory.setPassword("qjinfo@2017");
        connectionFactory.setVirtualHost("/");
		return connectionFactory;
		
	}
	
	
	/**
	 * RabbitMQ发送消息
	 * Sender
	 */
    public static void sendMessage(String message,String queueName) throws java.io.IOException, TimeoutException {
    	ConnectionFactory connectionFactory = MQUtils.connectionFactory();
        //3.通过sconnectionFactory创建一个连接connectionfig.
        Connection connection =  connectionFactory.newConnection();
        //4.通过connection创建一个频道channel
        Channel channel = connection.createChannel();
        //5.通过channel指定一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        //发送的消息  
//        message = "hello world!";
        //6.通过channel向队列中添加消息
        //将队列设置为持久化之后，还需要将消息也设为可持久化的，MessageProperties.PERSISTENT_TEXT_PLAIN
        channel.basicPublish("", queueName, null,message.getBytes());
        System.out.println("向" + queueName + "中添加了一条消息:" + message);
        //7.关闭频道
        channel.close();
        //8.关闭连接
        connection.close();
    }
    
    /**
     * RabbitMQ接受消息
     * Sender
     */
    public static void receiverMessage(String queueName) throws java.io.IOException, InterruptedException, TimeoutException {
    	
    	ConnectionFactory connectionFactory = MQUtils.connectionFactory();
    	//3.通过connectionFactory创建一个连接connection
        Connection connection = connectionFactory.newConnection();
        //4.通过connection创建一个频道channel
        Channel channel = connection.createChannel();
        //5.通过channel指定队列
        channel.queueDeclare(queueName, false, false, false, null);
        //与发送消息不同的地方
        //6.创建一个消费者队列consumer,并指定channel  
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //7.为channel指定消费者
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            //从consumer中获取队列中的消息,nextDelivery是一个阻塞方法,如果队列中无内容,则等待
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("接收到了" + queueName + "中的消息:" + message);
        }

    }
    
}

