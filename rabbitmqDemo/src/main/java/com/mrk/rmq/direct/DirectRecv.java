package com.mrk.rmq.direct;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectRecv {
    /**
     * 监听某个队列的消息
     */
    @RabbitListener(queues = "TestDirectQueue1")
    public void r1(User user){
        System.out.println("direct1接收消息：" + user);
    }

    /**
     * 此处r11和上面的r1方法同时监听TestDirectQueue1队列
     * 但是消息只能被消费一次，竞争关系。不会两个方法都消费（执行两次）
     */
    @RabbitListener(queues = "TestDirectQueue1")
    public void r11(User user){
        System.out.println("direct11接收消息：" + user);
    }

    @RabbitListener(queues = "TestDirectQueue2")
    public void r2(User user){
        System.out.println("direct2接收消息：" + user);
    }
}
