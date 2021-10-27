package com.mrk.rmq.fanout;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutRecv {

    @RabbitListener(queues="fanout.A")
    public void processA(User user) {
        System.out.println("ReceiveA:"+user);
    }

    /**
     * 此处processAA和上面的processA方法同时监听fanout.A队列
     * 但是消息只能被消费一次，竞争关系。不会两个方法都消费（执行两次）
     */
    @RabbitListener(queues="fanout.A")
    public void processAA(User user) {
        System.out.println("ReceiveAA:"+user);
    }

    @RabbitListener(queues="fanout.B")
    public void processB(User user) {
        System.out.println("ReceiveB:"+user);
    }

    @RabbitListener(queues="fanout.C")
    public void processC(User user) {
        System.out.println("ReceiveC:"+user);
    }
}
