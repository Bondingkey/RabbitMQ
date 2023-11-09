package com.gzc.spring.rabbitmq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  15:12  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ConsumerTest {

    @Test
    public void testConsumer(){
        while (true){


        }
    }
}
