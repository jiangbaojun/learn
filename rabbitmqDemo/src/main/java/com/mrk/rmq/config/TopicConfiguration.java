package com.mrk.rmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic模式配置
 */
@Configuration
public class TopicConfiguration {
    /********************topic模式的交换机*************************/
    /** 声明3个队列 */
    @Bean
    public Queue TestTopicQueue1() {
        return new Queue("TestTopicQueue1",true);
    }
    @Bean
    public Queue TestTopicQueue2() {
        return new Queue("TestTopicQueue2",true);
    }
    @Bean
    public Queue TestTopicQueueAll() {
        return new Queue("TestTopicQueueAll",true);
    }

    /** 声明一个交换机 */
    @Bean
    TopicExchange TestTopicExchange() {
        return new TopicExchange("TestTopicExchange",true,false);
    }


    /** 绑定关系 */
    @Bean
    Binding bindingTopic1() {
        return BindingBuilder.bind(TestTopicQueue1()).to(TestTopicExchange()).with("test.q1");
    }
    @Bean
    Binding bindingTopic2() {
        return BindingBuilder.bind(TestTopicQueue2()).to(TestTopicExchange()).with("test.q2");
    }
    @Bean
    Binding bindingTopicAll() {
        return BindingBuilder.bind(TestTopicQueueAll()).to(TestTopicExchange()).with("test.*");
    }

}
