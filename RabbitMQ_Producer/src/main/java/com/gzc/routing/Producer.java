package com.gzc.routing;

import com.gzc.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  09:56  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
//路由模式(定向模式)
public class Producer {

    public static final String EXCHANGE = "routing_Ex";
    public static final String QUEUE_1 = "routing_1";
    public static final String QUEUE_2 = "routing_2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT,true,false,false,null);
        //声明队列
        channel.queueDeclare(QUEUE_1,true,false,false,null);
        channel.queueDeclare(QUEUE_2,true,false,false,null);
        //绑定队列并设置路由Key
        channel.queueBind(QUEUE_1,EXCHANGE,"error");//路由Key为error就用这个队列
        channel.queueBind(QUEUE_2,EXCHANGE,"info");
        channel.queueBind(QUEUE_2,EXCHANGE,"error");
        channel.queueBind(QUEUE_2,EXCHANGE,"routing");
        //发送消息
        //String error_emm ="两个都有";
        //channel.basicPublish(EXCHANGE,"error",null,error_emm.getBytes());
        String info_emm ="第二个有";
        channel.basicPublish(EXCHANGE,"info",null,info_emm.getBytes());
        //关闭资源
        ConnectionUtil.closeResource(channel,connection);
    }
}
