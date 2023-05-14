package com.example.chat.ChatController.client;

import com.example.chat.ChatController.user.ChatUser;

public interface UserStatusListener {

    void onUserConnected(ChatUser user);
    void onUserDisconnected(ChatUser user);
}
