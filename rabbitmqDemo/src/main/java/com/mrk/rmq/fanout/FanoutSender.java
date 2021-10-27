package com.mrk.rmq.fanout;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * fanout模式
 * 通常发布/订阅模式，就是用fanout交换机
 * 消息会发送给和fanout绑定的队列（但是同一个队列，多个监听者依然是竞争关系，不会消费多次）
 *
 * 实现发布/订阅效果，是指每个订阅者都对应不同的队列。
 * 可以在消费端声明新的队列，手动绑定到交换机上。见FanoutRecv2示例
 */
@RestController
@RequestMapping("/fanout")
public class FanoutSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(){
        rabbitTemplate.convertAndSend("TestFanoutExchange",null, new User("小强1",2));
    }
}
