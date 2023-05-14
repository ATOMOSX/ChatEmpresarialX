package com.example.chat.server;

import com.example.chat.ChatMessage;
import com.example.chat.client.ClientThread;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private int port;
    private ChatServer chatServer;
    private Socket clientSocket;
    private ObjectOutput outputStream;

    public ServerThread(Socket clientSocket, ChatServer chatServer) {
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;

        try {
            // Inicializar la salida de mensajes hacia el cliente
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error initializing output stream: " + e.getMessage());
        }
    }


    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(clientSocket, (ClientThread.MessageListener) chatServer);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
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