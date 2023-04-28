package com.mrk.amq.common;

import com.alibaba.fastjson.JSON;
import com.mrk.amq.properties.DataChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSendService {

    @Autowired
    private MessageTemplate messageTemplate;

    /**
     * 发布路由消息
     * 相同系统，只有一个节点收到消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:25
     */
    public void sendRoutingMessage(String destination, Object data) {
        messageTemplate.sendRoutingMessage(destination, getDataChange(data));
    }

    /**
     * 发布订阅消息
     * 所有系统、所有节点都会收到消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:25
     */
    public void sendPublishMessage(String destination, Object data) {
        messageTemplate.sendPublishMessage(destination, getDataChange(data));
    }

    /**
     * 发布点对点消息
     * 无论系统，所有节点，只有一个节点收到消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:25
     */
    public void sendPointMessage(String destination, Object data) {
        messageTemplate.sendPointMessage(destination, getDataChange(data));
    }


    /**
     * 模拟内部封装对象
     * 数据传输采用文本
     * @param data 数据
     * @return com.mrk.amq.properties.DataChange
     * @date 2022/9/30 16:28
     */
    private String getDataChange(Object data) {
        DataChange dataChange = new DataChange();
        dataChange.setData(data);
        dataChange.setSourceSystemId(1);
        dataChange.setStudyId(2);
        dataChange.setSponsorId(3);
        return JSON.toJSONString(dataChange);
    }

}
