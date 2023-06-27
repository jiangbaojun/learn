package com.mrk.amq.customer;

import com.mrk.amq.common.annotation.MyJmsListener;
import com.mrk.amq.common.constant.MessageMode;
import com.mrk.amq.properties.FromModel;
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

    /**
     * 第一个参数是床底的参数。
     * 后面的参数可以自动注入空值。自动填充FromModel类型参数
     * @param result
     * @param fromModel
     */
    @MyJmsListener(destination= POINT_NAME, mode = MessageMode.POINT)
    public void receiveMsg1(User result, FromModel fromModel) {
        System.out.println("queue point:"+result);
        System.out.println(fromModel);
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
