package com.example.chat;

import com.example.chat.user.ChatUser;

public class ChatMessage {

    private ChatUser sender;
    private ChatUser receiver;
    private String content;

    public ChatMessage(ChatUser sender, ChatUser receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public ChatUser getSender() {
        return sender;
    }

    public void setSender(ChatUser sender) {
        this.sender = sender;
    }

    public ChatUser getReceiver() {
        return receiver;
    }

    public void setReceiver(ChatUser receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
