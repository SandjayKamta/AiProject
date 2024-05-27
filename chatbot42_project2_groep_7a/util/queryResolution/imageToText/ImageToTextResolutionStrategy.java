package com.example.chatbot42_project2_groep_7a.util.queryResolution.imageToText;

import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionForm;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionResult;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategy;
import javafx.scene.image.Image;

public class ImageToTextResolutionStrategy implements QueryResolutionStrategy {
    @Override
    public QueryResolutionResult resolve(QueryResolutionForm queryForm) {
        Object queryData = queryForm.getQueryData();
        Image image = (Image) queryData;
        String imageName = image.getUrl();

        System.out.println("Image name: " + imageName);

        if (imageName.contains("chatbot_42")) {
            return new QueryResolutionResult("chatbot42 #1 AI bot.\n");
        } else if (imageName.contains("hhs")) {
            return new QueryResolutionResult("hhs is een hogeschool.\n");
        } else if (imageName.contains("java")) {
            return new QueryResolutionResult("Java is een programmeertaal.\n");
        } else if (imageName.contains("pikachu")) {
            return new QueryResolutionResult("Pikachu is een Pokémon.\n");
        }
        return new QueryResolutionResult("Image not found.\n");
    }
}
