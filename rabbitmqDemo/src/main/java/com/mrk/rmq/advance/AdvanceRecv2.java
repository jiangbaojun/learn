package com.mrk.rmq.advance;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 消费端2
 * 接收不同类型的消息
 * @RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，
 * 具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "AdvanceQueue"),
                exchange = @Exchange(value = "AdvanceExchange", type = "topic"),
                key = "adv.t"
        )
)
@Component
public class AdvanceRecv2 {

    /**
     * 接收map类型的参数
     */
    @RabbitHandler
    public void t1(Map map, Channel channel, Message message){
        System.out.println("Map:"+map);
    }
    /**
     * 接收String类型的参数
     */
    @RabbitHandler
    public void t1(String msg, Channel channel, Message message){
        System.out.println("String:"+msg);
    }
    /**
     * 接收List类型的参数
     */
    @RabbitHandler
    public void t1(List<String> list, Channel channel, Message message){
        System.out.println("list:"+list);
    }
    /**
     * 接收数组类型的参数
     */
    @RabbitHandler
    public void t1(String[] arr, Channel channel, Message message){
        System.out.println("Arr:"+arr);
    }

}
