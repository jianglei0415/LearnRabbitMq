package com.jl.learn.rabbitmq.demo2;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author jianglei
 * @date 2020/2/17 22:18
 * 消息有效时间TTL
 */
public class RabbitProducer3 {
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-message-ttl", 6000);
        channel.queueDeclare("queueName", true, false, false, arg);
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);
        builder.expiration("60000");
        AMQP.BasicProperties properties = builder.build();
        channel.basicPublish("exchangeName", "routingKey", true, properties, "ttlTestMessage".getBytes());
        channel.close();
        connection.close();
    }
}
