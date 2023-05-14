package com.example.chat.server;

import com.example.chat.ChatMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private int port;
    private List<ServerThread> clientThreads;

    public ChatServer(int port) {
        this.port = port;
        this.clientThreads = new ArrayList<>();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Chat server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress().getHostName());

            ServerThread clientThread = new ServerThread(clientSocket, this);
            clientThreads.add(clientThread);
            clientThread.start();
        }
    }

    public void removeClientThread(ServerThread clientThread) {
        clientThreads.remove(clientThread);
    }

    public void broadcastMessage(ChatMessage message) {
        for (ServerThread clientThread : clientThreads) {
            clientThread.sendMessage(message);
        }
    }
}