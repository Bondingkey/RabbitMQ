package com.gzc.simple;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/07  15:25  周二
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置
        connectionFactory.setHost("192.168.150.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPassword("11111111");
        //创建链接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //消费者不需要声明队列,如果队列不存在可以自动创建一个队列,如果写,参数必须和生产者一样
        //channel.queueDeclare("simple_queue",true,false,false,null);
        //创建Con对象
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /*
               回调方法,当收到消息后,会自动执行该方法
               1. consumerTag：标识
               2. envelope：获取一些信息,交换机,路由key...
               3. properties：配置信息
               4. body：数据
            */
            @Override
            public void handleDelivery(java.lang.String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
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
        //消费消息
        /*
        basicConsume(String queue, boolean autoAck, Consumer callback)
        参数：
            1. queue：队列名称
            2. autoAck：是否自动确认 ,类似咱们发短信,发送成功会收到一个确认消息
            3. callback：回调对象
         */

        channel.basicConsume("simple_queue",true,defaultConsumer);

        //关闭资源
//        channel.close();
//        connection.close();

    }
}
