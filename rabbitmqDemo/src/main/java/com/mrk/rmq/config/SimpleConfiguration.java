package com.mrk.rmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * simple（HelloWorld）模式配置
 */
@Configuration
public class SimpleConfiguration {
    //定义队列
    @Bean
    public Queue TestSimpleQueue() {
        return new Queue("TestSimpleQueue",true);
    }
}
