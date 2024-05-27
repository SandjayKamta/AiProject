package com.example.chatbot42_project2_groep_7a.db;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseUser = "2c0bzt2k54zw0ww43u95";
        String databasePassword = "pscale_pw_FJyvLXx03Sn7v9sxBWZCPwS5hz53GrOULsNQl7YiYX7";
        String url= "jdbc:mysql://aws.connect.psdb.cloud/chatbot?sslMode=VERIFY_IDENTITY";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        }catch (Exception e) {
            if (e.getMessage().contains("The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.")) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Login failed");
                    alert.setContentText("No internet connection.");
                    alert.showAndWait();
                });
            } else {
                e.printStackTrace();
            }
        }

        return databaseLink;
    }
}

