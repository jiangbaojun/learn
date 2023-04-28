package com.mrk.amq.common.constant;

public enum MessageMode {
    /**点对点模式。不管系统，所有节点，只有一个节点收到消息*/
    POINT(1),
    /**发布订阅模式。所有系统、所有节点都会收到消息*/
    PUBLISH(2),
    /**路由模式。相同系统，只有一个节点收到消息*/
    ROUTING(3);

    private Integer id;

    MessageMode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
