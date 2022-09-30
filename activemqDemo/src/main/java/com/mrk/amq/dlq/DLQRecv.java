package com.mrk.amq.dlq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 *
 * dlq 私信队列 接收
 */
@Component
public class DLQRecv {

    @JmsListener(destination="DLQ",  containerFactory = "topicListener")
    public void receiveMsg(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("接收到dlq消息 : "+text);
    }
}
