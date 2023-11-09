package com.gzc.spring.rabbitmq.listence;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  15:31  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class TopicLinstner1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
        System.out.println("message.getMessageProperties().getDeliveryTag() = " + message.getMessageProperties().getDeliveryTag());
        System.out.println("message.getMessageProperties().getDeliveryTag() = " + message.getMessageProperties().getDeliveryTag());
        System.out.println("message.getMessageProperties().getReceivedExchange() = " + message.getMessageProperties().getReceivedExchange());
        System.out.println("message.getMessageProperties().getReceivedRoutingKey() = " + message.getMessageProperties().getReceivedRoutingKey());
    }

}
