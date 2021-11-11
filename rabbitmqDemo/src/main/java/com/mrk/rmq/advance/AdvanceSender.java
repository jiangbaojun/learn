package com.mrk.rmq.advance;

import com.mrk.rmq.advance.detail.MyConfirmCallback;
import com.mrk.rmq.advance.detail.MyReturnsCallback;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: jiangbaojun
 * @Date: 2020/3/6 12:23
 *
 * 本示例未涉及的
 * 1、死信队列（延迟队列），参考：http://www.gxitsky.com/article/1604455229805099
 * 产生死信原因：
 *  消息被否定确认，使用 channel.basicNack 或 channel.basicReject ，并且此时requeue 属性被设置为false。
 *  消息在队列的存活时间超过设置的TTL时间。
 *  消息队列的消息数量已经超过最大队列长度。
 *
 */
@RestController
@RequestMapping("/advance")
public class AdvanceSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyConfirmCallback myConfirmCallback;
    @Autowired
    private MyReturnsCallback myReturnsCallback;

    @RequestMapping("/t")
    public void t(){
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t", "String参数");
        List<String> list = Arrays.asList("l1", "l2");
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t", list);
        Map<String, String> map = new HashMap<>();
        map.put("a","1");
        map.put("b","2");
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t", map);
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t", new String[]{"s1","s2"});

    }

    /**
     * 发送其他类型的消息，例如map
     */
    @RequestMapping("/send1")
    public void t1(){
        Map<String, Object> map = new HashMap<>();
        map.put("messageId",UUID.randomUUID().toString());
        map.put("messageData","map消息!");
        map.put("createTime",new Date().getTime());
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t1",map);
    }
    /**
     * 发送Message类型的消息
     */
    @RequestMapping("/send2")
    public void t2(){
        Message message = MessageBuilder.withBody("message类型消息".getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .build();
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t2",message);
    }

    /**
     * 发送String类型的消息
     */
    @RequestMapping("/send3")
    public void t3(){
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t3","我是String类型消息");
    }

    /**
     * confirm确认消息
     */
    @RequestMapping("/send31")
    public void t31(){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(new Date().getTime()+"");
        rabbitTemplate.setConfirmCallback(myConfirmCallback);
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t3","我是String类型消息", correlationData);
        //模拟错误
//        rabbitTemplate.convertAndSend("AdvanceExchange_notExist","adv.t3","我是String类型消息");
    }
    /**
     * return消息
     */
    @RequestMapping("/send32")
    public void t32(){
        rabbitTemplate.setConfirmCallback(myConfirmCallback);
        rabbitTemplate.setReturnsCallback(myReturnsCallback);
//        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t3","我是String类型消息");
        //模拟错误
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.notExist","我是String类型消息");
    }

    /**
     * 消费端ack确认消息
     */
    @RequestMapping("/send4")
    public void t4(){
        rabbitTemplate.setConfirmCallback(myConfirmCallback);
        rabbitTemplate.setReturnsCallback(myReturnsCallback);
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t4","我是send4消息");
    }

    /**
     * 设置请求头
     */
    @RequestMapping("/send5")
    public void t5(){
        Message message = MessageBuilder.withBody("message build消息体".getBytes())
                .setHeader("x-test1", "testHead")
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .build();
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t5",message);
    }

    /**
     * 设置过期
     * 如果队列和消息都设置了过期时间，以小的为准
     */
    @RequestMapping("/send6")
    public void t6(){
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setExpiration("5000"); // 设置过期时间，单位：毫秒
//        byte[] msgBytes = "测试消息自动过期".getBytes();
//        Message message = new Message(msgBytes, messageProperties);

        Message message = MessageBuilder.withBody("有过期时间的message".toString().getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setExpiration("5000")
                .build();
        //消息有过期时间。消息进入队列头部开始计算时间
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.t6",message);
        //队列限定，队列内所有消息有过期时间
        rabbitTemplate.convertAndSend("AdvanceExchange","adv.ttl","ttl队列消息");
    }

}
