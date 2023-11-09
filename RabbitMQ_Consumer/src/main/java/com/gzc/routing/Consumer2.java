package com.gzc.routing;

import com.gzc.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  09:14  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class Consumer2 {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //获取消息者标识
                System.out.println("consumerTag = " + consumerTag);
                //获取交换机名字
                System.out.println("envelope.getExchange() = " + envelope.getExchange());
                //获取路由key
                System.out.println("envelope.getRoutingKey() = " + envelope.getRoutingKey());
                //获取第几条消息
                System.out.println("envelope.getDeliveryTag() = " + envelope.getDeliveryTag());
                //获取其他属性
                System.out.println("properties = " + properties);
                //获取消息内容
                System.out.println("new String(body) = " +new String(body));
            }
        };
        channel.basicConsume("routing_2",true,defaultConsumer);
    }
}
