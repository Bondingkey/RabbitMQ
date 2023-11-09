package com.gzc.fanout;

import com.gzc.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/08  09:31  周三
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
//发布订阅模式(广播模式)
public class Producer {
    public static final String QUEUE_1 = "fanout1";
    public static final String QUEUE_2 = "fanout2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明交换机
        /*
       exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
       参数：
        1. exchange：交换机名称
        2. type：交换机类型
            DIRECT("direct"),：定向
            FANOUT("fanout"),：扇形（广播）,发送消息到每一个与之绑定队列。
            TOPIC("topic"),通配符的方式
            HEADERS("headers");参数匹配
        3. durable：是否持久化
        4. autoDelete：自动删除
        5. internal：内部使用。 一般false
        6. arguments：参数
        */
        channel.exchangeDeclare("fanout_ex", BuiltinExchangeType.FANOUT,true,false,false,null);
        //声明队列
        channel.queueDeclare(QUEUE_1,true,false,false,null);
        channel.queueDeclare(QUEUE_2,true,false,false,null);
        //交换机绑定队列
        channel.queueBind(QUEUE_1,"fanout_ex","");
        channel.queueBind(QUEUE_2,"fanout_ex","");
        //开始生产,发送消息
        String message = "该条消息会被交换机转发";
        channel.basicPublish("fanout_ex","",null,message.getBytes());
        //关闭资源
        ConnectionUtil.closeResource(channel,connection);


    }
}
