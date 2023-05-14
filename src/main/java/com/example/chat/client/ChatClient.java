package com.example.chat.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Application {

    private Socket socket;
    private TextArea chatArea;
    private TextField messageField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label nameLabel = new Label("Username:");
        TextField nameField = new TextField();
        Button connectButton = new Button("Connect");

        GridPane loginPane = new GridPane();
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));
        loginPane.add(nameLabel, 0, 0);
        loginPane.add(nameField, 1, 0);
        loginPane.add(connectButton, 2, 0);

        VBox chatPane = new VBox();
        chatPane.setSpacing(10);
        chatPane.setPadding(new Insets(25, 25, 25, 25));
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        ScrollPane scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        messageField = new TextField();
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendMessage());
        chatPane.getChildren().addAll(scrollPane, messageField, sendButton);

        BorderPane root = new BorderPane();
        root.setCenter(chatPane);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        connectButton.setOnAction(event -> {
            String username = nameField.getText().trim();
            if (!username.isEmpty()) {
                try {
                    socket = new Socket("localhost", 8080);
                    chatArea.appendText("Connected to server!\n");
                    ClientThread clientThread = new ClientThread(socket, this::onMessageReceived);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setOnCloseRequest(event -> {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Platform.exit();
        });

    }

    private void onMessageReceived(String message) {
        chatArea.appendText(message + "\n");
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && socket != null) {
            ClientThread.sendMessage(message);
            messageField.clear();
        }
    }

}

