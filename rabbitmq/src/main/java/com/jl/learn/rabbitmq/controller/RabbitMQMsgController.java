package com.jl.learn.rabbitmq.controller;

import com.jl.learn.rabbitmq.enumaration.DelayTypeEnum;
import com.jl.learn.rabbitmq.producer.DelayMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Objects;

/**
 * @author jianglei
 * @date 2020/2/18 0:46
 */
@Slf4j
@Controller
@RequestMapping("/rabbitmq")
public class RabbitMQMsgController {
    @Autowired
    private DelayMessageProducer producer;

    @RequestMapping("/sendMsg")
    public void sendMsg(String msg, Integer delayType) {
        log.info("当前时间：{},收到请求，msg:{},delayType:{}", new Date(), msg, delayType);
        producer.sendMsg(msg, Objects.requireNonNull(DelayTypeEnum.getDelayTypeEnumByValue(delayType)));
    }
}
