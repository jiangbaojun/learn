package com.mrk.amq.ps;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * 发布订阅 接收
 */
@Component
public class PsRecv {

//    @JmsListener(destination="my.ps.test.topic", containerFactory = "topicListener")
    public void receiveMsg(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("接收到ps消息1 : "+text);
    }

//    @JmsListener(destination="my.ps.test.topic")
    public void receiveMsg1(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("接收到ps消息2 : "+text);
    }
}
