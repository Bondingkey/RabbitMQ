package com.gzc.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/07  14:51  周二
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
//简单模式的成产者
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.150.100");//IP地址
        connectionFactory.setPort(5672);//端口号
        connectionFactory.setVirtualHost("/");//虚拟主机
        connectionFactory.setUsername("admin");//用户名
        connectionFactory.setPassword("11111111");//密码

        //创建链接
        Connection connection = connectionFactory.newConnection();

        //创建信道
        Channel channel = connection.createChannel();

        //声明队列
        /**
         * queue      参数1：队列名称
         * durable    参数2：是否定义持久化队列,当mq重启之后,还在
         * exclusive  参数3：是否独占本次连接
         *            ① 是否独占,只能有一个消费者监听这个队列
         *            ② 当connection关闭时,是否删除队列
         * autoDelete 参数4：是否在不使用的时候自动删除队列,当没有consumer时,自动删除
         * arguments  参数5：队列其它参数
         */
        channel.queueDeclare("simple_queue",true,false,false,null);

        //创建要发布的消息
        String immage = "你好,小兔子";

        //发布
        /**
         * 参数1：交换机名称,如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：配置信息
         * 参数4：消息内容
         */

        channel.basicPublish("","simple_queue",null,immage.getBytes());

        //关闭资源
        channel.close();
        connection.close();
    }
}
