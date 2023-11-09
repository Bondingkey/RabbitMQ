package com.gzc;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/09  10:22  周四
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@SpringBootTest
public class SpringBootRabbitTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //测试发送消息
    @Test
    public void test1(){
        rabbitTemplate.convertAndSend("SpringBoot_Ex","springboot.haha","测试Springboot整合RabbitMQ");
    }
}
