package com.gzc.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/09  10:11  周四
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@SpringBootConfiguration//配置类
public class RabbitMQConfig {

    public static final String EX = "SpringBoot_Ex";
    public static final String QUEUE = "SpringBoot_Queue";

    //创建交换机
    @Bean
    public Exchange getEx(){
        return ExchangeBuilder.topicExchange(EX).durable(true).build();
    }

    //创建队列
    @Bean
    public Queue getQueue(){
        return QueueBuilder.durable(QUEUE).build();
    }

    //绑定交换机
    @Bean
    public Binding binging(Exchange exchange,Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("springboot.#").noargs();
    }
}
