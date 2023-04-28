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

    /**
     * 默认情况下，spring.jms.pub-sub-domain配置是false，点对点模式。如果不指定containerFactory，会导致收不到消息
     * 如果spring.jms.pub-sub-domain=true，无论是否指定containerFactory都会收到消息，发布订阅
     */
    //@JmsListener(destination="my.ps.test.topic")
    public void receiveMsg1(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("接收到ps消息2 : "+text);
    }

//    @JmsListener(destination="my.ps.test.topic", containerFactory = "topicListener", selector = "my_tag='A'")
    public void receiveMsg3(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("接收到ps消息my_tag : "+text);
    }

//    @JmsListener(destination="my.ps.test.topic", containerFactory = "topicListener", selector = "my_tag='A5'")
    public void receiveMsg4(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("接收到ps消息my_tag : "+text);
    }
}
