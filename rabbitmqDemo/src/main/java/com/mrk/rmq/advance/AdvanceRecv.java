package com.mrk.rmq.advance;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


/**
 * 消费端
 */
@Component
public class AdvanceRecv {

    /**
     * 接收map类型的参数
     */
    @RabbitListener(queues = "AdvanceQueue1")
    public void t1(Map map, Channel channel, Message message){
        System.out.println("AdvanceQueue1:"+map);
    }

    /**
     * 接收Message
     */
    @RabbitListener(queues = "AdvanceQueue2")
    public void t2(@Payload String body, @Headers Map<String,Object> headers, Message message, String deliveryTag, Channel channel) {
        System.out.println("AdvanceQueue2:"+body);
    }

    /**
     * 接收简单的字符串
     * 声明并创建队列，手动绑定
     */
    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "AdvanceQueue_t3"),
            exchange = @Exchange(value = "AdvanceExchange", type = "topic"),
            key = "adv.t3"
        )
    )
    public void t3(String message) {
        System.out.println("AdvanceQueue_t3:"+message);
    }

    /**
     * 消息ack确认
     * 注意，这个ack回执，并不是针对发送端，而是针对rabbitmq服务。发送到并不会知晓
     * 配置spring.rabbitmq.listener.simple.retry.enabled=true，开启手动ack
     * 如果指定手动ack，但是却没有进行确认，查看队列消息状态，会发现有消息处于unacked状态，消息会进行重发,通过spring.rabbitmq.listener.direct.retry进行设置
     * 调用basicAck会消费消息。消息将从队列删除
     * 调用basicReject、basicNack也会消费消息，通过requeue参数指明是否需要将消息放回队列，如果放回队列将会重试
     * 监听方法抛出异常，消息会被放回队列，进行重试
     */
    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "AdvanceQueue_t4"),
            exchange = @Exchange(value = "AdvanceExchange", type = "topic"),
            key = "adv.t4"
        )
    )
    public void t4(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.println("AdvanceQueue_t4:"+message.getMessageProperties().getDeliveryTag()+":"+msg);
//            int a = 3/0;
            //如果此处不确认，那么消息处于unacked状态，下次启动，会重发消息。
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 请求头
     */
    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "AdvanceQueue_t5"),
            exchange = @Exchange(value = "AdvanceExchange", type = "topic"),
            key = "adv.t5"
        )
    )
    public void t5(String msg, Channel channel, Message message, @Headers Map<String,Object> headers) {
        System.out.println(msg);
        Map<String, Object> headers1 = message.getMessageProperties().getHeaders();
        System.out.println(headers);
        System.out.println(headers1);
    }
    /**
     * 过期时间
     */
    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "AdvanceQueue_t6"),
            exchange = @Exchange(value = "AdvanceExchange", type = "topic"),
            key = "adv.t6"
        )
    )
    public void t6(String msg) {
        System.out.println(msg);
    }
    @RabbitListener(queues = "AdvanceQueueTTL")
    public void t61(String msg) {
        System.out.println("AdvanceQueueTTL队列："+msg);
    }


}
