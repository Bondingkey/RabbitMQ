package com.gzc.spring.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  15:01  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ProducerTest {

    //注入rabbit-template对象
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //测试发送消息
    @Test
    public void testSimple() {
        //向简单队列发消息
        rabbitTemplate.convertAndSend("simple_queue", "测试简单模式");
    }

    //测试广播模式
    @Test
    public void testfanout() {
        rabbitTemplate.convertAndSend("fanout_ex", "", "测试fanout");
    }

    //测试主题模式
    @Test
    public void testtopic() {
        rabbitTemplate.convertAndSend("topic_ex", "bj.gzc", "在第一个");
        rabbitTemplate.convertAndSend("topic_ex", "gzc.bj", "在第二个");
    }

    //测试定向确认模式
    @Test
    public void testconfirm() {
        //定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    System.out.println("投递成功");
                } else {
                    System.out.println("投递失败,原因:"+cause);
                    //做一些处理
                }
            }
        });
        //成功
        //rabbitTemplate.convertAndSend("direct_ex","confirm","测试定向确认模式");
        //失败,设置不存在的交换机名字
        for (int i = 0 ;i<10;i++) {
            rabbitTemplate.convertAndSend("direct_ex", "confirm", "测试定向确认模式");
        }
    }

    //测试退回模式
    @Test
    public void testreturn(){
        //必须设置强制退回,否则不会调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            //只有失败才调用
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("message.getBody() = " + new String(message.getBody()));
                System.out.println("replyText = " + replyText);
                System.out.println("replyCode = " + replyCode);
                System.out.println("exchange = " + exchange);
                System.out.println("routingKey = " + routingKey);
            }
        });
        //成功
        //rabbitTemplate.convertAndSend("direct_ex","confirm","测试定向退回 模式");
        //失败,设置不存在的交换机名字
        rabbitTemplate.convertAndSend("direct_ex", "confirm111", "测试定向确认模式");
    }

    //消息过期时间
    @Test
    public void testttl(){
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置消息的有效时间
                message.getMessageProperties().setExpiration("5000");
                return message;
            }
        };
        rabbitTemplate.convertAndSend("ttl_ex", "test.aaa", "ttl过期时间",messagePostProcessor);
    }


    /**
     * 发送测试死信消息：
     */
    @Test
    public void testDlx2(){
        //1. 测试过期时间,死信消息
        //rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？");

//        //2. 测试长度限制后,消息死信
//        for (int i = 0; i < 11; i++) {
//            rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？"+i);
//        }

        //3. 测试消息拒收
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","我是一条消息,我会死吗？");
    }





}
