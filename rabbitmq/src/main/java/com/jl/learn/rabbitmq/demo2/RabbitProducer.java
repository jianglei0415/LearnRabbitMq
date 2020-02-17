package com.jl.learn.rabbitmq.demo2;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jianglei
 * @date 2020/2/17 22:18
 */
public class RabbitProducer {
    private static final String EXCHANGE_NAME = "exchange_demo2";
    private static final String ROUTING_KEY = "routing_key_demo2";
    private static final String QUEUE_NAME = "queue_demo2";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "mandatory test";
        channel.basicPublish(EXCHANGE_NAME, "", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.addReturnListener((i, s, s1, s2, basicProperties, bytes) -> {
            String message1 = new String(bytes);
            System.out.println("Basic.Return返回的结果是：" + message1);
        });
        channel.close();
        connection.close();
    }
}
