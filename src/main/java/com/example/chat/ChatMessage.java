package com.example.chat;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String sender;
    private String message;
    private Type type;
    private String username;

    public ChatMessage(String sender, String message, Type type) {
        this.sender = sender;
        this.message = message;
        this.type = type;
    }

    public ChatMessage() {
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[" + sender + "]: " + message;
    }

    public enum Type {
        TEXT
    }
}
