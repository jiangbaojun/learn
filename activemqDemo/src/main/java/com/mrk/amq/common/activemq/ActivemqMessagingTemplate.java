package com.mrk.amq.common.activemq;

import com.mrk.amq.common.MessageTemplate;
import com.mrk.amq.common.util.MessageUtil;
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
     * 发送点对点消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:22
     */
    @Override
    public void sendPointMessage(String destination, Object data) {
        ActiveMQDestination activeMQQueue = new ActiveMQQueue(MessageUtil.getPrefix()+destination);
        convertAndSend(activeMQQueue, data);
    }

    /**
     * 发送发布订阅消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:22
     */
    @Override
    public void sendPublishMessage(String destination, Object data) {
        ActiveMQDestination activeMQTopic = new ActiveMQTopic(MessageUtil.getPrefix()+destination);
        convertAndSend(activeMQTopic, data);
    }

    /**
     * 发送路由消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:22
     */
    @Override
    public void sendRoutingMessage(String destination, Object data) {
        ActiveMQDestination activeMQTopic = new ActiveMQTopic("VirtualTopic."+destination);
        convertAndSend(activeMQTopic, data);
    }
}
