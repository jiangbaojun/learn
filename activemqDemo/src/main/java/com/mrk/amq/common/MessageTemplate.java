package com.mrk.amq.common;

/**
 * 消息发送模板
 * @date 2022/9/30 16:09
 */
public interface MessageTemplate {

    void sendPointMessage(String destination, Object data);

    void sendPublishMessage(String destination, Object data);

    void sendRoutingMessage(String destination, Object data);
}
