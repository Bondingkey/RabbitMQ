package com.gzc.topic;

import com.gzc.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import javax.imageio.stream.ImageInputStream;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  10:18  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
//通配符模式(主题模式)
public class Producer {

    public static final String TOPIC_EX = "topic_Ex";
    public static final String QUEUE_1 = "topic_1";
    public static final String QUEUE_2 = "topic_2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(TOPIC_EX, BuiltinExchangeType.TOPIC,true,false,false,null);
        //声明队列
        channel.queueDeclare(QUEUE_1,true,false,false,null);
        channel.queueDeclare(QUEUE_2,true,false,false,null);
        //绑定
        channel.queueBind(QUEUE_1,TOPIC_EX,"error.#");//多个单词
        channel.queueBind(QUEUE_2,TOPIC_EX,"error.*");//一个单词
        //发送消息
//        String text = "两个单词";
//        channel.basicPublish(TOPIC_EX,"error.test.test2",null,text.getBytes());
        String text = "一个单词";
        channel.basicPublish(TOPIC_EX,"error.test",null,text.getBytes());
        //关闭资源
        ConnectionUtil.closeResource(channel,connection);
    }

}
