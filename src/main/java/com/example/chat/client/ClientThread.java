package com.example.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private static PrintWriter writer;
    private BufferedReader reader;
    private MessageListener messageListener;

    public ClientThread(Socket socket, MessageListener messageListener) {
        this.socket = socket;
        this.messageListener = messageListener;

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                messageListener.onMessageReceived(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message) {
        writer.println(message);
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
