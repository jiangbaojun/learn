package com.mrk.amq.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

/**
 * 点对点 发送
 * sender
 */
@RestController
@RequestMapping("/simple")
public class SimpleSender {

    @Autowired
    private JmsTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("testSimpleQueue")
    private Queue queue;

    @RequestMapping("/send")
    public void sendMsg(@RequestParam(value = "msg") String msg) {
        System.out.println("发送消息内容 :" + msg);
        jmsMessagingTemplate.convertAndSend(queue, msg);
    }
}
