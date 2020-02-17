package com.jl.learn.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jianglei
 * @date 2020/2/18 0:16
 */
@Configuration
public class RabbitMQConfig {
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

    /**
     * 声明延迟队列交换器
     */
    @Bean("delayExchange")
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    /**
     * 声明死信队列交换器
     */
    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明延迟队列A
     */
    @Bean("delayQueueA")
    public Queue delayQueueA() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEA_ROUTING_KEY);
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(DELAY_QUEUEA_NAME).withArguments(args).build();
    }

    /**
     * 声明延迟队列B
     */
    @Bean("delayQueueB")
    public Queue delayQueueB() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUEB_ROUTING_KEY);
        args.put("x-message-ttl", 60000);
        return QueueBuilder.durable(DELAY_QUEUEB_NAME).withArguments(args).build();
    }

    /**
     * 声明死信队列A
     */
    @Bean("deadLetterQueueA")
    public Queue deadLetterQueueA() {
        return new Queue(DEAD_LETTER_QUEUEA_NAME);
    }

    /**
     * 声明死信队列B
     */
    @Bean("deadLetterQueueB")
    public Queue deadLetterQueueB() {
        return new Queue(DEAD_LETTER_QUEUEB_NAME);
    }

    /**
     * 绑定延迟队列A
     */
    @Bean
    public Binding delayBindingA(@Qualifier("delayQueueA") Queue queue,
                                 @Qualifier("delayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUEA_ROUTING_KEY);
    }

    /**
     * 绑定延迟队列B
     */
    @Bean
    public Binding delayBindingB(@Qualifier("delayQueueB") Queue queue,
                                 @Qualifier("delayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUEB_ROUTING_KEY);
    }

    /**
     * 绑定死信队列A
     */
    @Bean
    public Binding deadLetterBindingA(@Qualifier("deadLetterQueueA") Queue queue,
                                      @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEA_ROUTING_KEY);
    }

    /**
     * 绑定死信队列B
     */
    @Bean
    public Binding deadLetterBindingB(@Qualifier("deadLetterQueueB") Queue queue,
                                      @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUEB_ROUTING_KEY);
    }
}
