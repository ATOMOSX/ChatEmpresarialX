package com.example.chat.ChatController.server;

import com.example.chat.ChatController.ChatMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private ServerSocket serverSocket;
    private List<ServerThread> clientThreads = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Chat server started on port " + port);

        while (true) {
            System.out.println("Waiting for clients to connect...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress());
            ServerThread clientThread = new ServerThread(clientSocket, this);
            clientThreads.add(clientThread);
            clientThread.start();
        }
    }

    public void broadcastMessage(ChatMessage message) {
        for (ServerThread clientThread : clientThreads) {
            try {
                clientThread.sendMessage(message);
            } catch (IOException e) {
                System.err.println("Error broadcasting message to client: " + e.getMessage());
            }
        }
    }

    public void removeClientThread(ServerThread clientThread) {
        clientThreads.remove(clientThread);
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        try {
            chatServer.start(5000);
        } catch (IOException e) {
            System.err.println("Error starting chat server: " + e.getMessage());
        }
    }
}
