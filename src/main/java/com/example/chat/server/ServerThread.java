package com.example.chat.server;

import com.example.chat.ChatMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {

    private ChatServer chatServer;
    private Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public ServerThread(Socket clientSocket, ChatServer chatServer) throws IOException {
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void run() {
        try {
            String username = inputStream.readUTF();
            chatServer.broadcastMessage(new ChatMessage(username, " has joined the chat.", ChatMessage.Type.TEXT));

            while (true) {
                String messageText = inputStream.readUTF();
                ChatMessage message = new ChatMessage(username, messageText, ChatMessage.Type.TEXT);
                chatServer.broadcastMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            chatServer.removeClientThread(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void sendMessage(ChatMessage message) throws IOException {
        outputStream.writeUTF(message.toString());
        outputStream.flush();
    }
}
