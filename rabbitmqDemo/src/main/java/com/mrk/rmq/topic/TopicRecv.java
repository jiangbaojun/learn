package com.mrk.rmq.topic;

import com.mrk.rmq.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听不同的队列
 * 在配置中绑定队列和routingKey的关系，发送时指定routingKey
 */
@Component
public class TopicRecv {

    @RabbitListener(queues = "TestTopicQueue1")
    public void listerQueueA(User u){
        System.out.println("queueA" + u);
    }

    /**
     * 此处listerQueueAA和上面的listerQueueA方法同时监听TestTopicQueue1队列
     * 但是消息只能被消费一次，竞争关系。不会两个方法都消费（执行两次）
     */
    @RabbitListener(queues = "TestTopicQueue1")
    public void listerQueueAA(User u){
        System.out.println("queueAA" + u);
    }

    @RabbitListener(queues = "TestTopicQueue2")
    public void listerQueueB(User u){
        System.out.println("queueB" + u);
    }

    @RabbitListener(queues = "TestTopicQueueAll")
    public void listerQueueAll(User u){
        System.out.println("queueAll" + u);
    }
}
