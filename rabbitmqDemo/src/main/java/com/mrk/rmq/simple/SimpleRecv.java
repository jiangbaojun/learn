package com.mrk.rmq.simple;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class SimpleRecv {

    @RabbitListener(queues = "TestSimpleQueue")
    public void t1(User u){
        System.out.println("TestSimpleQueue: " + u);
    }
}
