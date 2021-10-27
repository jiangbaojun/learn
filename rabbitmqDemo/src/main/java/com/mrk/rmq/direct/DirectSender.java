package com.mrk.rmq.direct;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路由模式
 * @Author: jiangbaojun
 * @Date: 2020/3/6 12:23
 */
@RestController
@RequestMapping("/direct")
public class DirectSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(){
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting1",new User("TestDirectRouting1小强",2));
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting2",new User("TestDirectRouting2小强",3));
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",new User("TestDirectRouting小强",4));
    }
}
