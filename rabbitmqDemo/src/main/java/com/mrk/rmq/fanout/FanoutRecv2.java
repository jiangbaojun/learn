package com.mrk.rmq.fanout;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutRecv2 {

    /**
     * 手动声明一个队列，并且与交换机绑定
     * 本例，会自动创建psQueue队列
     * @param user
     */
    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(value = "psQueue", durable = "true"),
                exchange = @Exchange(value = "TestFanoutExchange", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
            )
    )
    public void receive(User user) {
        System.out.println("annotation bind"+user);
    }

    /**
     * 使用临时队列
     */
    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue,
                exchange = @Exchange(value = "TestFanoutExchange", type = ExchangeTypes.FANOUT)
            )
    )
    public void receiveTemp(User user) {
        System.out.println("temp annotation bind"+user);
    }
}
