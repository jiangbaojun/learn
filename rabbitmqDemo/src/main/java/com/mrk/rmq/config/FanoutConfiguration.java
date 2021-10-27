package com.mrk.rmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * fanout模式配置
 */
@Configuration
public class FanoutConfiguration {
    /********************fanout模式的交换机*************************/
    /** 定义3个队列 */
    @Bean(name="Amessage")
    public Queue AMessage() {
        return new Queue("fanout.A");
    }
    @Bean(name="Bmessage")
    public Queue BMessage() {
        return new Queue("fanout.B");
    }
    @Bean(name="Cmessage")
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    /** 定义1个交换机 */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("TestFanoutExchange");
    }

    /** 绑定关系*/
    @Bean
    Binding bindingExchangeA(@Qualifier("Amessage") Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(@Qualifier("Bmessage") Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeC(@Qualifier("Cmessage") Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }
}
