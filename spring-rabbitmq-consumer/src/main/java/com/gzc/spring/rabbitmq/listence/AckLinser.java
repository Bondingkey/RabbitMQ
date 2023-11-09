package com.gzc.spring.rabbitmq.listence;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/11/09  08:03  周四
 * @Project: RabbitMQ
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class AckLinser implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取消息标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("new String(message.getBody()) = " + new String(message.getBody()));
            //处理业务逻辑出现异常
            //int i= 10/0;
            //如果没有异常手动确认
            //第一个参数:消息标识,第二个:是否批量确认
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            // ④ 拒绝签收
             /*
            第三个参数：requeue：重回队列。
            设置为true,则消息重新回到queue,broker会重新发送该消息给消费端
             */
            channel.basicNack(deliveryTag,true,true);
        }
    }
}
