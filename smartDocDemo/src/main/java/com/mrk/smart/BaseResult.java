package com.mrk.smart;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseResult<T> implements Serializable {

    /**结果标志*/
    private boolean success = false;

    /**消息*/
    private String message;

    /**数据*/
    private T data;

    /**响应代码*/
    private String code;

    /**时间*/
    private String timestamp;
}