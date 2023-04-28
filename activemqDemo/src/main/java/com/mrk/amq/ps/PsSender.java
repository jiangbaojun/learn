package com.mrk.amq.ps;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;

/**
 * 发布订阅 发送
 * sender
 */
@RestController
@RequestMapping("/ps")
public class PsSender {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    @Autowired
    @Qualifier("testPsTopic")
    private Topic topic;

    @RequestMapping("/send")
    public void sendMsg(@RequestParam(value = "msg") String msg) {
        System.out.println("发送消息内容 :" + msg);
        jmsMessagingTemplate.convertAndSend(topic, msg);
    }

    @Autowired
    @Qualifier("connectionFactory")
    private ActiveMQConnectionFactory activeMQConnectionFactory;

    /**
     * 使用selector
     * message.setStringProperty设置键值对
     * 监听端，可以通过注解 @JmsListener的selector属性指定
     * 对于没有声明selector属性的@JmsListener。可以接受所有selector的消息
     */
    @RequestMapping("/send/selector")
    public void sendMsgSelector(@RequestParam(value = "msg") String msg) throws JMSException {
        System.out.println("发送消息内容selector:" + msg);
        TopicConnection connection = activeMQConnectionFactory.createTopicConnection();
        connection.start();
        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicPublisher publisher = session.createPublisher(topic);
        TextMessage message = session.createTextMessage();
        message.setText("你好，"+msg);
        //设置消息属性：标记、过滤
        message.setStringProperty("my_tag","A");
        message.setStringProperty("my_tagb","B");
        publisher.send(message);

        session.close();
        connection.close();
    }
}
