package com.mrk.rmq.advance.detail;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * confirm确认机制
 * 判定消息是否成功发送到交换机上
 * 回调函数要实现rabbitTemplate中的confirmCallback接口
 * 需要配置：spring.rabbitmq.publisher-confirm-type=correlated
 */
@Component
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        /**
         *
         * @param correlationData 消息信息
         * @param b  确认标识：true表示交换机已经确认收到消息，false表示没有收到消息
         * @param s  如果没有收到消息，则指定为MQ服务器exchange消息没有收到的原因，如果已经收到则指定为null
         */
        if (b){
            System.out.println("exchange消息收到,内容为："+correlationData);
        }else {
            System.out.println("exchange消息未收到，原因为："+s);
        }
    }
}
