package com.example.chat.ChatController.client;

import com.example.chat.ChatController.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private MessageListener messageListener;

    public ChatClient(String serverAddress, int serverPort, MessageListener messageListener) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.messageListener = messageListener;
    }

    public void connect() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        System.out.println("Connected to server " + serverAddress + " on port " + serverPort);

        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        Thread receivingThread = new Thread(() -> {
            while (true) {
                try {
                    ChatMessage message = (ChatMessage) inputStream.readObject();
                    messageListener.onMessageReceived(message);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error receiving message: " + e.getMessage());
                }
            }
        });

        receivingThread.start();
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }

    public void sendMessage(ChatMessage message) {
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    public interface MessageListener {
        void onMessageReceived(ChatMessage message);
    }
}
