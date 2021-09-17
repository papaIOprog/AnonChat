package com.papaio.anonchat.model;

public class Message {

    private String toUser;
    private String body;

    public Message() {
    }

    public Message(String toUser, String body) {
        this.toUser = toUser;
        this.body = body;
    }

    public Message(String body) {
        this.body = body;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}