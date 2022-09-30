package com.mrk.amq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

import javax.jms.Queue;
import javax.jms.Topic;


/**
 * 基础配置类
 */
//@Configuration
public class ActiveMQBaseConfig {

    @Value("${spring.activemq.broker-url}")
    private  String brokerUrl;


    /**
     * 用于点对点模式
     * @return
     */
    @Bean("testSimpleQueue")
    public Queue testSimpleQueue() {
        return new ActiveMQQueue("my.simple.test.queue");
    }

    /**
     * 用于发布订阅模式
     * 可以指定 spring.jms.pub-sub-domain 配置属性为true，使用发布订阅模式
     *
     * 也可以不指定spring.jms.pub-sub-domain属性。创建自定义的JmsListenerContainerFactory，如本例的jmsListenerContainerTopic方法，出案件的bean名称topicListener
     * 然后在接收端JmsListener注解中指定containerFactory。 如：@JmsListener(destination="my.ps.test.topic", containerFactory = "topicListener")
     * @return
     */
    @Bean("testPsTopic")
    public Topic testPsTopic() {
        return new ActiveMQTopic("my.ps.test.topic");
    }

    /**
     * 配置名字为givenConnectionFactory的连接工厂
     * @return
     */
    @Bean("givenConnectionFactory")
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        // 设置重发机制
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setUseExponentialBackOff(Boolean.TRUE);
        // 消息处理失败重新处理次数,默认为6次
        policy.setMaximumRedeliveries(2);
        // 重发时间间隔,默认为1秒
        policy.setInitialRedeliveryDelay(1000L);
        policy.setBackOffMultiplier(2);
        policy.setMaximumRedeliveryDelay(1000L);
        activeMQConnectionFactory.setRedeliveryPolicy(policy);
        return activeMQConnectionFactory;
    }
    /**
     * 在Queue模式中，对消息的监听需要对containerFactory进行配置
     */
    @Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ActiveMQConnectionFactory givenConnectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        // 关闭事务
        factory.setSessionTransacted(false);
        // 手动确认消息
        factory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(givenConnectionFactory);
        return factory;
    }

    /**
     * 在发布订阅模式中，对消息的监听需要对containerFactory进行配置
     */
    @Bean("topicListener")
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory givenConnectionFactory){
        //设置为发布订阅模式, 默认情况下使用生产消费者方式
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        // 手动确认消息
        bean.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        bean.setConnectionFactory(givenConnectionFactory);
        return bean;
    }

}