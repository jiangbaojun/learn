package com.mrk.amq.simple;

import org.springframework.stereotype.Component;

/**
 * 点对点 接收
 */
@Component
public class SimpleRecv {

//    @JmsListener(destination = "my.simple.test.queue",containerFactory = "queueListener")
    public void receiveMsg1(String text) {
        System.out.println("接收到消息1 : "+text);
    }

//    @JmsListener(destination = "my.simple.test.queue")
    public void receiveMsg2(String text) {
        System.out.println("接收到消息2 : "+text);
    }
}
