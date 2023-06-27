package com.mrk.amq.customer;

import com.mrk.amq.common.MessageSendService;
import com.mrk.amq.properties.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义入口 发布订阅 发送
 * 如果使用VirtualTopic模式。本例
 *      发送topic信息，也就是将消息发送到VirtualTopic.test2，调用sendMsg2方法。接收端：
 *          @MyJmsListener(destination=QUEUE_NAME, pubSub = false) 相同的QUEUE_NAME，只有一个客户端收到消息
 *          @MyJmsListener(destination=TOPIC_NAME, pubSub = true)  所有客户端收到消息
 *
 *      发送queue信息，也就是将消息发送到Consumer.a.VirtualTopic.test2，调用sendMsg1方法。接收端：
 *  *       @MyJmsListener(destination=QUEUE_NAME, pubSub = false) 收不到消息
 *  *       @MyJmsListener(destination=TOPIC_NAME, pubSub = true)  收不到消息
 */
@RestController
@RequestMapping("/customer")
public class CustomerSender {

    private final String POINT_NAME = "my.point";
    private final String PUBLISH_NAME = "my.publish";
    private final String ROUTING_NAME = "myRouting";

    @Autowired
    private MessageSendService messageSendService;

    @RequestMapping("/point/send")
    public void sendMsg1(@RequestParam(value = "msg") String msg) {
        User user = new User(12,"点对点消息");
        System.out.println("发消息："+user);
        messageSendService.sendPointMessage(POINT_NAME, user);
        //也可以传递数组
        //messageSendService.sendPointMessage(POINT_NAME, new String[]{msg});
    }

    @RequestMapping("/publish/send")
    public void sendMsg2(@RequestParam(value = "msg") String msg) {
        User user = new User(12, "订阅消息");
        System.out.println("发消息："+user);
        messageSendService.sendPublishMessage(PUBLISH_NAME, user);
    }

    @RequestMapping("/routing/send")
    public void sendMsg3(@RequestParam(value = "msg") String msg) {
        User user = new User(12, "路由消息");
        System.out.println("发消息："+user);
        messageSendService.sendRoutingMessage(ROUTING_NAME, user);
    }
}
