package com.mrk.rmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * work模式配置
 */
@Configuration
public class WorkConfiguration {
    //定义队列
    @Bean
    public Queue TestWorkQueue() {
        return new Queue("TestWorkQueue",true);
    }
}
