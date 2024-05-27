package com.example.chatbot42_project2_groep_7a.util;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class ChatManagerImages {
    private static ChatManagerImages instance;
    private HashMap<Integer, Map<Label, ImageView>> chatMapImage;
    private HashMap<Integer, Map<ImageView, Label>> chatMapImageToText;
    private ChatManagerImages() {
        chatMapImage = new HashMap<>();
        chatMapImageToText = new HashMap<>();
    }

    public HashMap<Integer, Map<Label, ImageView>> getChatMapImages() {
        return chatMapImage;
    }

    public HashMap<Integer, Map<ImageView, Label>> getChatMapImagesToText() {
        return chatMapImageToText;
    }

    public static ChatManagerImages getInstance() {
        if (instance == null) {
            instance = new ChatManagerImages();
        }
        return instance;
    }
    public void saveChatMessagesImages(Integer chatId, Map<Label, ImageView> chatMessages) {
        chatMapImage.put(chatId, chatMessages);
        System.out.println(chatId+"@@@@@@@@@@@@@@@@@ ");
    }
    public Map<Label, ImageView> getChatMessagesImages(Integer chatId) {
        return chatMapImage.get(chatId);
    }

    public void saveChatMessagesImagesToText(Integer chatId, Map<ImageView, Label> chatMessages) {
        chatMapImageToText.put(chatId, chatMessages);
        System.out.println(chatId+"@@@@@@@@@@@@@@@@@ ");
    }

    public Map<ImageView, Label> getChatMessagesImagesToText(Integer chatId) {
        return chatMapImageToText.get(chatId);
    }

}

