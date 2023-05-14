package com.example.chat;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    private String sender;
    private String message;
    private Type type;


    public ChatMessage(String sender, String message, Type text) {
        this.sender = sender;
        this.message = message;
        this.type = text;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[" + sender + "]: " + message;
    }

    public enum Type {TEXT}
}
