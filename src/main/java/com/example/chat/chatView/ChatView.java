package com.example.chat.chatView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatView extends Application {

    private TextArea chatArea;
    private TextField inputField;
    private Button sendButton;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Chat area
        chatArea = new TextArea();
        chatArea.setEditable(false);
        ScrollPane scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        VBox chatBox = new VBox(scrollPane);
        chatBox.setPrefSize(500, 300);
        root.setCenter(chatBox);

        // Input area
        inputField = new TextField();
        inputField.setPrefWidth(400);
        sendButton = new Button("Send");
        sendButton.setPrefWidth(80);
        VBox inputBox = new VBox(inputField, sendButton);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.CENTER_RIGHT);
        root.setBottom(inputBox);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat View");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




