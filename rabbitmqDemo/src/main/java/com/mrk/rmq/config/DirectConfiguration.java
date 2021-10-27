package com.mrk.rmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由模式配置
 */
@Configuration
public class DirectConfiguration {
    /********************direct模式的交换机*************************/
    //定义两个队列
    @Bean
    public Queue TestDirectQueue1() {
        return new Queue("TestDirectQueue1",true);
    }
    @Bean
    public Queue TestDirectQueue2() {
        return new Queue("TestDirectQueue2",true);
    }


    //Direct交换机
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange");
    }

    //绑定关系
    @Bean
    Binding bindingDirect1() {
        return BindingBuilder.bind(TestDirectQueue1()).to(TestDirectExchange()).with("TestDirectRouting1");
    }
    @Bean
    Binding bindingDirect2() {
        return BindingBuilder.bind(TestDirectQueue2()).to(TestDirectExchange()).with("TestDirectRouting2");
    }
}
