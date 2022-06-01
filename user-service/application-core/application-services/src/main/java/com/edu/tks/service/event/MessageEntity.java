package com.edu.tks.service.event;

public class MessageEntity {
    private String content;

    public MessageEntity(String content) {
        this.content = content;
    }

    public MessageEntity() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
