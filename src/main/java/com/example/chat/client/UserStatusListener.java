package com.example.chat.client;

import com.example.chat.user.ChatUser;

public interface UserStatusListener {

    void onUserConnected(ChatUser user);
    void onUserDisconnected(ChatUser user);
}
