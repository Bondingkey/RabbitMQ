package com.gzc.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/09  10:30  周四
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@Component
public class SpringBootRabbitMQLinster {

    @RabbitListener(queues = "SpringBoot_Queue")
    public void getMessage(Message message){
        System.out.println("new String(message.getBody()) = " + new String(message.getBody()));

    }
}
