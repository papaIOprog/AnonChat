package com.papaio.anonchat.models;

public class ServerMessage extends Message {
    private String fromUser;
    private MessageTypes type;

    public ServerMessage() {
    }

    public ServerMessage(String fromUser, String toUser, MessageTypes type, String body) {
        super(toUser, body);
        this.fromUser = fromUser;
        this.type = type;
    }

    public ServerMessage(String fromUser, MessageTypes type, String body) {
        super(body);
        this.fromUser = fromUser;
        this.type = type;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public MessageTypes getType() {
        return type;
    }

    public void setType(MessageTypes type) {
        this.type = type;
    }
}
