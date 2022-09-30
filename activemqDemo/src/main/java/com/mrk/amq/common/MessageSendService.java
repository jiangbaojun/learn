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
     * 发布订阅消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:25
     */
    public void sendPubSubMessage(String destination, Object data) {
        messageTemplate.sendPubSubMessage(destination, getDataChange(data));
    }

    /**
     * 发布点对点消息
     * @param destination 目标
     * @param data 数据
     * @date 2022/9/30 16:25
     */
    public void sendToPointMessage(String destination, Object data) {
        messageTemplate.sendToPointMessage(destination, getDataChange(data));
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
