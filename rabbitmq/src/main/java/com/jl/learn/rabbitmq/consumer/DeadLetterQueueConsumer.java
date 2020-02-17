package com.jl.learn.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author jianglei
 * @date 2020/2/18 0:35
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {
    private static final String DELAY_EXCHANGE_NAME = "delay.queue.demo.business.exchange";
    private static final String DELAY_QUEUEA_NAME = "delay.queue.demo.business.queuea";
    private static final String DELAY_QUEUEB_NAME = "delay.queue.demo.business.queueb";
    private static final String DELAY_QUEUEA_ROUTING_KEY = "delay.queue.demo.business.queuea.routingkey";
    private static final String DELAY_QUEUEB_ROUTING_KEY = "delay.queue.demo.business.queueb.routingkey";
    private static final String DEAD_LETTER_EXCHANGE = "delay.queue.demo.dead.letter.exchange";
    private static final String DEAD_LETTER_QUEUEA_ROUTING_KEY = "delay.queue.demo.dead.letter.delay_10s.routingkey";
    private static final String DEAD_LETTER_QUEUEB_ROUTING_KEY = "delay.queue.demo.dead.letter.delay_60s.routingkey";
    private static final String DEAD_LETTER_QUEUEA_NAME = "delay.queue.demo.dead.letter.queuea";
    private static final String DEAD_LETTER_QUEUEB_NAME = "delay.queue.demo.dead.letter.queueb";

    @RabbitListener(queues = DEAD_LETTER_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{}，死信队列A收到信息：{}", new Date(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DEAD_LETTER_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{}，死信队列B收到信息：{}", new Date(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
