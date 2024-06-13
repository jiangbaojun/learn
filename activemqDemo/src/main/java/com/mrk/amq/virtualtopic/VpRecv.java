package com.mrk.amq.virtualtopic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * VirtualTopic 发布订阅 接收
 * VirtualTopic是一种内嵌机制，默认开启无需配置，使用者通过使用特殊命名的Topic和Queue来使用VirtualTopic机制，默认的命名方式是：
 *      Topic:   VirtualTopic.*
 *      Queue:  Consumer.*.VirtualTopic.*
 * https://www.cnblogs.com/Peter2014/p/11065869.html
 */
@Component
public class VpRecv {

    @JmsListener(destination="Consumer.a.VirtualTopic.test")
    //destination值可以使用application.yml中的配置属性
//    @JmsListener(destination="Consumer.${testVar.xyz}.VirtualTopic.test")
    public void receiveMsg(TextMessage textMessage) throws JMSException {
        String text = textMessage.getText();
        System.out.println("接收到vp消息 : "+text);
    }
}
