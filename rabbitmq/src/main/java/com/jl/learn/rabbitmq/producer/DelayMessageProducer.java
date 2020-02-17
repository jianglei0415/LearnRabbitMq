package com.jl.learn.rabbitmq.producer;

import com.jl.learn.rabbitmq.enumaration.DelayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jianglei
 * @date 2020/2/18 0:41
 */
@Slf4j
@Component
public class DelayMessageProducer {
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg, DelayTypeEnum type) {
        switch (type) {
            case DELAY_10s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEA_ROUTING_KEY, msg);
                break;
            case DELAY_60s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEB_ROUTING_KEY, msg);
                break;
        }
    }
}
