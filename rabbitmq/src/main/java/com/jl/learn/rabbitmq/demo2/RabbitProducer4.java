package com.jl.learn.rabbitmq.demo2;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author jianglei
 * @date 2020/2/17 22:18
 * 死信队列
 */
public class RabbitProducer4 {
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
        channel.exchangeDeclare("exchange.dlx", "direct", true);
        channel.exchangeDeclare("exchange.normal", "fanout", true);
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-message-ttl", 10000);
        arg.put("x-dead-letter-exchange", "exchange.dlx");
        arg.put("x-dead-letter-routing-key", "routingKey");
        channel.queueDeclare("queue.normal", true, false, false, arg);
        channel.queueBind("queue.normal", "exchange.normal", "");
        channel.queueDeclare("queue.dlx", true, false, false, null);
        channel.queueBind("queue.dlx", "exchange.dlx", "routingKey");
        channel.basicPublish("exchange.normal", "routingKey", MessageProperties.PERSISTENT_TEXT_PLAIN, "dlx".getBytes());
        channel.close();
        connection.close();
    }
}
