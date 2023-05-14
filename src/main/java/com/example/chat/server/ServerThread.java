package com.example.chat.server;

import com.example.chat.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private ChatServer chatServer;
    private ObjectOutputStream outputStream;

    public ServerThread(Socket clientSocket, ChatServer chatServer) {
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ChatMessage message = (ChatMessage) in.readObject();
                chatServer.broadcastMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error reading message: " + e.getMessage());
        } finally {
            chatServer.removeClientThread(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                // Do nothing
            }
        }
    }

    public void sendMessage(ChatMessage message) {
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
