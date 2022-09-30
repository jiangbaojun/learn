package com.mrk.amq.customer;

import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.properties.User;
import org.springframework.stereotype.Component;

/**
 *
 * 发布订阅 接收
 */
@Component
public class CustomerRecv {

    private final String TOPIC_NAME = "my.annotation.test.topic";
    private final String QUEUE_NAME = "my.annotation.test.queue";

//    private final String TOPIC_NAME = "VirtualTopic.test2";
//    private final String QUEUE_NAME = "Consumer.a.VirtualTopic.test2";

    @MyJmsListener(destination=QUEUE_NAME, pubSub = false)
    public void receiveMsg1(User result) {
        System.out.println("queue:"+result);
    }

    @MyJmsListener(destination=TOPIC_NAME, pubSub = true)
    public void receiveMsg2(User result) {
        System.out.println("topic:"+result);
    }

}
