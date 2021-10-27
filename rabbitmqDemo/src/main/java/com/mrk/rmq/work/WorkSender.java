package com.mrk.rmq.work;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * work模式
 * sender
 */
@RestController
@RequestMapping("/work")
public class WorkSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(){
        rabbitTemplate.convertAndSend("TestWorkQueue", new User("小明work", 8));
    }
}
