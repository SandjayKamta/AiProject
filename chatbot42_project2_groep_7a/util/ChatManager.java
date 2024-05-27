package com.example.chatbot42_project2_groep_7a.util;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManager {
    private static ChatManager instance;

    private String newTopic = "";

    private HashMap<Integer, Map<String, Object>> chatMap;

    public static Map <String, Map<Integer, String>> chatTopicMap = new HashMap<>();

    private ChatManager() {
        chatMap = new HashMap<>();
        chatTopicMap.put("TextToText", new HashMap<>());
        chatTopicMap.put("TextToImage", new HashMap<>());
        chatTopicMap.put("ImageToText", new HashMap<>());
    }
    public HashMap<Integer, Map<String, Object>> getChatMap() {
        return chatMap;
    }

    public static ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }
        return instance;
    }

    public void saveChatMessages(Integer chatId, Map<String, Object> chatMessages) {
        chatMap.put(chatId, chatMessages);
    }

    public Map<String, Object> getChatMessages(Integer chatId) {
        return chatMap.get(chatId);
    }

    public String changeTopic(String chatType, int currentChatId){
        Stage changeTopic = new Stage();

        changeTopic.setTitle("Change Topic");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        Label label = new Label("Change Topic");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextField textField = new TextField();
        textField.setPromptText("Enter new topic");

        Button button = new Button("Change");
        button.setOnAction(event -> {
            newTopic = textField.getText();
            if (newTopic != null && !newTopic.isEmpty()) {
                changeTopic.close();
            }
        });

        vBox.getChildren().addAll(label, textField, button);

        Scene scene = new Scene(vBox, 300, 200);
        changeTopic.setScene(scene);
        changeTopic.showAndWait();

        Map<Integer, String> topicMap = chatTopicMap.get(chatType);
        if (topicMap == null) {
            topicMap = new HashMap<>();
            chatTopicMap.put(chatType, topicMap);
        }

        topicMap.put(currentChatId, newTopic);

        System.out.println(chatTopicMap);
        return newTopic;
    }
}

