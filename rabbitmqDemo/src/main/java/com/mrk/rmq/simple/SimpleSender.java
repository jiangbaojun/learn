package com.mrk.rmq.simple;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * simple模式
 * sender
 */
@RestController
@RequestMapping("/simple")
public class SimpleSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(){
        rabbitTemplate.convertAndSend("TestSimpleQueue", new User("小明simple", 8));
    }
}
