package com.mrk.amq.common;

/**
 * 消息发送模板
 * @date 2022/9/30 16:09
 */
public interface MessageTemplate {

    void sendToPointMessage(String destination, Object data);

    void sendPubSubMessage(String destination, Object data);
}
