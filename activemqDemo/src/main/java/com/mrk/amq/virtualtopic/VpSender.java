package com.mrk.amq.virtualtopic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Topic;

/**
 * VirtualTopic 发布订阅 发送
 * 必须把消息发送到topic，本例testVpTopic bean
 * sender
 */
@RestController
@RequestMapping("/vp")
public class VpSender {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    @Autowired
    @Qualifier("testVpTopic")
    private Topic topic;

    @RequestMapping("/send")
    public void sendMsg(@RequestParam(value = "msg") String msg) {
        System.out.println("发送消息内容 :" + msg);
        jmsMessagingTemplate.convertAndSend(topic, msg);
    }
}
