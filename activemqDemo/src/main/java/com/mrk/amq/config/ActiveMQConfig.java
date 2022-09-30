package com.mrk.amq.config;

import com.mrk.amq.common.activemq.ActivemqMessagingTemplate;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Topic;


/**
 * 配置类
 */
@Configuration
public class ActiveMQConfig {

    @Value("${customer.message.broker-url}")
    private  String brokerUrl;

    /**
     * 用于点对点模式
     */
    @Bean("testSimpleQueue")
    public Queue testSimpleQueue() {
        return new ActiveMQQueue("my.simple.test.queue");
    }

    /**
     * 用于发布订阅模式
     */
    @Bean("testPsTopic")
    public Topic testPsTopic() {
        return new ActiveMQTopic("my.ps.test.topic");
    }

//    /**
//     * 用于自定义topic
//     */
//    @Bean("customerPsTopic")
//    public Topic testCustomerTopic() {
//        return new ActiveMQTopic("my.annotation.test.topic");
//    }

    /**
     * 用于VirtualTopic发布订阅模式
     */
    @Bean("testVpTopic")
    public Topic testVpTopic() {
        return new ActiveMQTopic("VirtualTopic.test");
    }

    /**
     * 用于VirtualTopic发布订阅模式
     */
    @Bean("testVpQueue")
    public Topic testVpQueue() {
        return new ActiveMQTopic("Consumer.a.VirtualTopic.test");
    }



    @Bean(value="jmsTemplate")
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        PropertyMapper map = PropertyMapper.get();
        JmsTemplate template = new JmsTemplate(connectionFactory);
        return template;
    }
    
    @Bean(value="jmsMessagingTemplate")
    ActivemqMessagingTemplate jmsMessagingTemplate(JmsTemplate jmsTemplate) {
        ActivemqMessagingTemplate messagingTemplate = new ActivemqMessagingTemplate(jmsTemplate);
        return messagingTemplate;
    }

    /**
     * 配置名字为connectionFactory的连接工厂
     */
    @Bean("connectionFactory")
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
        //信任包，传递对象
        activeMQConnectionFactory.setTrustAllPackages(true);
        return activeMQConnectionFactory;
    }
    /**
     * 在Queue模式中，对消息的监听需要对containerFactory进行配置
     */
    @Bean("queueListener")
    public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ActiveMQConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        // 关闭事务
        factory.setSessionTransacted(false);
        // 手动确认消息
        factory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    /**
     * 在发布订阅模式中，对消息的监听需要对containerFactory进行配置
     */
    @Bean("topicListener")
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory){
        //设置为发布订阅模式, 默认情况下使用生产消费者方式
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        // 手动确认消息
        bean.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

}