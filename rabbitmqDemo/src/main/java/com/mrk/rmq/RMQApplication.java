package com.mrk.rmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://www.cnblogs.com/qianyi525/p/13916410.html
 *
 * RabbitMQ工作模式：
 * 1、简单模式 HelloWorld 一个生产者、一个消费者，不需要设置交换机（使用默认的交换机）
 * 2、工作队列模式 Work Queue 一个生产者、多个消费者（竞争关系），不需要设置交换机（使用默认的交换机）
 * 3、发布订阅模式 Publish/subscribe 需要设置类型为fanout的交换机，并且交换机和队列进行绑定，当发送消息到交换机后，交换机会将消息发送到绑定的队列
 * 4、路由模式 Routing 需要设置类型为direct的交换机，交换机和队列进行绑定，并且指定routing key，当发送消息到交换机后，交换机会根据routing key将消息发送到对应的队列
 * 5、通配符模式 Topic 需要设置类型为topic的交换机，交换机和队列进行绑定，并且指定通配符方式的routing key，当发送消息到交换机后，交换机会根据routing key将消息发送到对应的队列
 */
@SpringBootApplication
public class RMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(RMQApplication.class, args);
    }

}
