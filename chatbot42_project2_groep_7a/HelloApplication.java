package com.example.chatbot42_project2_groep_7a;

import com.example.chatbot42_project2_groep_7a.ui.Login;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Login login = new Login(primaryStage);
        primaryStage.setTitle("ChatBot42");
        primaryStage.getIcons().add(new Image(("DALL_E_2023-05-22_15.05.24_-_app_icon_for_chatbot_42-removebg-preview.png")));
        login.show();

    }

    public static void main(String[] args) {
        launch();
    }
}