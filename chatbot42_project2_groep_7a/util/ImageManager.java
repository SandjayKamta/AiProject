package com.example.chatbot42_project2_groep_7a.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private Map<String, Image> imagePaths;

    public ImageManager() {
        imagePaths = new HashMap<>();
        imagePaths.put("start", ImagePaths.START_IMAGE);
        imagePaths.put("42", ImagePaths.BEDRIJF42);
        imagePaths.put("hhs", ImagePaths.HHS_AULA);
        imagePaths.put("haagse_hogeschool", ImagePaths.HHS_AULA);
        imagePaths.put("java", ImagePaths.JAVA);
        imagePaths.put("pikachu", ImagePaths.PIKACHU);
    }

    public void setImageOnImageView(ImageView imageView, String keyword) {
        Image imagePath = imagePaths.get(keyword);
        if (imagePath != null) {
            Image image = imagePath;
            imageView.setImage(image);
        }
    }
}