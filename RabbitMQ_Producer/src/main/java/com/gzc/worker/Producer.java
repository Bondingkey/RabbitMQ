package com.gzc.worker;

import com.gzc.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  09:02  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
//工作模式的生产者
public class Producer {
    public static void main(String[] args) throws Exception {

        //获取链接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明队列与队列属性
        channel.queueDeclare("worker_queue",true,false,false,null);
        //发送多条消息
        for (int i = 0; i < 5; i++) {
            String emm = "消息"+(i+1);
            channel.basicPublish("","worker_queue",null, emm.getBytes());
        }
        //关闭资源
        channel.close();
        connection.close();
    }
}
