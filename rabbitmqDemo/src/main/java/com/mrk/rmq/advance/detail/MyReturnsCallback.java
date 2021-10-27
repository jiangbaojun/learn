package com.mrk.rmq.advance.detail;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * return确认机制
 * 消息到达交换机后，判定路由转发到队列是否成功
 * 回调函数要实现RabbitTemplate.ReturnCallback接口
 * 如果成功不回调
 * 需要配置：spring.rabbitmq.publisher-confirm-type=correlated
 */
@Component
public class MyReturnsCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        /**
         *
         * @param message 消息信息,因为message传递过来是字节，所以需要转换成字符串
         * @param replyCode 退回的状态码
         * @param replyText 退回的信息
         * @param exchange 交换机
         * @param routingKey 路由key
         */
        System.out.println("退回的消息是："+new String(message.getBody()));
        System.out.println("退回的状态码是："+replyCode);
        System.out.println("退回的信息是："+replyText);
        System.out.println("退回的交换机是："+exchange);
        System.out.println("退回的路由key是："+routingKey);
    }
}
