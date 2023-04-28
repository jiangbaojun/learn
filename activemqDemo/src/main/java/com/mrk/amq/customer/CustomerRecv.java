package com.mrk.amq.customer;

import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.common.constant.MessageMode;
import com.mrk.amq.properties.User;
import org.springframework.stereotype.Component;

/**
 *
 * 发布订阅 接收
 */
@Component
public class CustomerRecv {

    private final String POINT_NAME = "my.point";
    private final String PUBLISH_NAME = "my.publish";
    private final String ROUTING_NAME = "myRouting";

    @MyJmsListener(destination= POINT_NAME, mode = MessageMode.POINT)
    public void receiveMsg1(User result) {
        System.out.println("queue point:"+result);
    }

    @MyJmsListener(destination= PUBLISH_NAME, mode = MessageMode.PUBLISH)
    public void receiveMsg2(User result) {
        System.out.println("topic publish:"+result);
    }

    @MyJmsListener(destination=ROUTING_NAME, mode = MessageMode.ROUTING)
    public void receiveMsg3(User result) {
        System.out.println("topic routing:"+result);
    }

}
