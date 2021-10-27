package com.mrk.rmq.topic;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * topic模式
 * sender
 */
@RestController
@RequestMapping("/topic")
public class TopicSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(){
        rabbitTemplate.convertAndSend("TestTopicExchange","test1", new User("小明test1", 8));

        rabbitTemplate.convertAndSend("TestTopicExchange","test.q1", new User("小明test.q1", 9));

        rabbitTemplate.convertAndSend("TestTopicExchange","test.q2", new User("小明test.q2", 10));

        rabbitTemplate.convertAndSend("TestTopicExchange","test.abc", new User("小明test.abc", 11));
    }
}
