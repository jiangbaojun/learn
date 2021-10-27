package com.mrk.rmq.work;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class WorkRecv {

    @RabbitListener(queues = "TestWorkQueue")
    public void t1(User u){
        System.out.println("t1" + u);
    }

    @RabbitListener(queues = "TestWorkQueue")
    public void t2(User u){
        System.out.println("t2" + u);
    }
}
