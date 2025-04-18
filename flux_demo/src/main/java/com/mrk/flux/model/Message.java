package com.mrk.flux.model;

public class Message {
    private String content;

    // 构造函数、getter 和 setter 方法
    public Message() {}

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}