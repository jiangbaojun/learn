package com.mrk.rmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用topic类型的交换机
 * 高级用法配置
 */
@Configuration
public class AdvanceConfiguration {
    /********************topic模式的交换机*************************/
    /** 声明队列 */
    @Bean
    public Queue AdvanceQueue() {
        return new Queue("AdvanceQueue",true);
    }
    @Bean
    public Queue AdvanceQueue1() {
        return new Queue("AdvanceQueue1",true);
    }
    @Bean
    public Queue AdvanceQueue2() {
        return new Queue("AdvanceQueue2",true);
    }
    @Bean
    public Queue AdvanceQueueTTL() {
        //队列1分钟内没有使用，自动删除。没有使用是指没有消费者监听该队列。如果没有监听，即使不断向队列发送消息，队列依然会被过期删除
        //队列内的消息10秒钟没有消费，删除
        Queue advanceQueueTTL = QueueBuilder.nonDurable("AdvanceQueueTTL")
                .autoDelete().expires(60000)
                .ttl(10000).build();
        return advanceQueueTTL;
    }

    /** 声明一个交换机 */
    @Bean
    TopicExchange AdvanceExchange() {
        return new TopicExchange("AdvanceExchange",true,false);
    }


    /** 绑定关系 */
    @Bean
    Binding bindingAdvance() {
        return BindingBuilder.bind(AdvanceQueue()).to(AdvanceExchange()).with("adv.t");
    }
    @Bean
    Binding bindingAdvance1() {
        return BindingBuilder.bind(AdvanceQueue1()).to(AdvanceExchange()).with("adv.t1");
    }
    @Bean
    Binding bindingAdvance2() {
        return BindingBuilder.bind(AdvanceQueue2()).to(AdvanceExchange()).with("adv.t2");
    }
    @Bean
    Binding bindingAdvanceTTL() {
        return BindingBuilder.bind(AdvanceQueueTTL()).to(AdvanceExchange()).with("adv.ttl");
    }

}
