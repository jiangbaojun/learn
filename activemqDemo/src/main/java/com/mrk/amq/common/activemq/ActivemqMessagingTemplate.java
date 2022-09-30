package com.mrk.amq.common.activemq;

import com.mrk.amq.common.MessageTemplate;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

/**
 * activemq实现，发送入口
 * @author baojun.jiang
 * @date 2022-09-28 15:21
 */
public class ActivemqMessagingTemplate extends JmsMessagingTemplate implements MessageTemplate {

    public ActivemqMessagingTemplate(JmsTemplate jmsTemplate) {
        super(jmsTemplate);
    }

    /**
     * 发送消息到queue，用于点对点通信
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:22
     */
    @Override
    public void sendToPointMessage(String destination, Object data) {
        ActiveMQDestination activeMQQueue = new ActiveMQQueue(destination);
        convertAndSend(activeMQQueue, data);
    }

    /**
     * 发送消息到topic，用于发布订阅
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:22
     */
    @Override
    public void sendPubSubMessage(String destination, Object data) {
        ActiveMQDestination activeMQTopic = new ActiveMQTopic(destination);
        convertAndSend(activeMQTopic, data);
    }
}
