package com.papaio.anonchat.model;

import java.time.Instant;

public class Message {

    private boolean fired;
    private long timestamp;
    private String toUser;
    private String body;

    public Message() {
        this.fired = false;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public Message(String toUser, String body) {
        this();
        this.toUser = toUser;
        this.body = body;
    }

    public Message(String body) {
        this();
        this.body = body;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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