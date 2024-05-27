package com.example.chatbot42_project2_groep_7a.ui;

import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.db.Queries;
import com.example.chatbot42_project2_groep_7a.db.SHA256Encryption;
import com.example.chatbot42_project2_groep_7a.db.SqlExecutor;
import com.example.chatbot42_project2_groep_7a.ui.Settings.Settings;
import com.example.chatbot42_project2_groep_7a.util.Gebruiker;
import com.example.chatbot42_project2_groep_7a.util.OfflineInlogStrategy;
import com.example.chatbot42_project2_groep_7a.util.OnlineInlogStrategy;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login implements eventhandler {
    private Stage primaryStage;
    private ProgressIndicator progressIndicator;
    private TextField emailField;
    private PasswordField passwordField;
    private Label chatBot = new Label();
    private Button loginButton = new Button();
    private Button offlineButton = new Button("Offline");
    private Button onlineButton = new Button("Online");
    private Label register = new Label();
    private Image image =  new Image(("DALL_E_2023-05-22_15.05.24_-_app_icon_for_chatbot_42-removebg-preview.png"));
    private ImageView imageView = new ImageView(image);
    private VBox vbox = new VBox();
    private BorderPane root = new BorderPane(vbox);
    private Scene scene = new Scene(root, 1280, 720);
    public static String staticEmail;

    private static final Queries  queries = new Queries(new SqlExecutor(new DatabaseConnection()));

    public VBox getVbox() {
        return vbox;
    }

    public Login(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.progressIndicator = new ProgressIndicator();
        this.emailField = new TextField();
        this.passwordField = new PasswordField();
    }
    public void Event1(){
        register.setOnMouseClicked(event -> {
            Register registerInterface = new Register(primaryStage);
            registerInterface.show();
            System.out.println("registreer");
        });
    }
    public void Event2(){
        offlineButton.setOnMouseClicked(mouseEvent ->{
            vbox.getChildren().remove(passwordField);
            vbox.getChildren().remove(offlineButton);
            vbox.getChildren().add(4, onlineButton);
        });
        onlineButton.setOnMouseClicked(mouseEvent -> {
            vbox.getChildren().remove(onlineButton);
            vbox.getChildren().add(3, passwordField);
            vbox.getChildren().add(5, offlineButton);
        });
    }
    public void Event3(){
        //offline inloggen met enter:
        emailField.setOnKeyPressed(offlineKeyEvent ->{
            if (offlineKeyEvent.getCode().equals(KeyCode.ENTER) && !getVbox().getChildren().contains(passwordField)) {
                offlineInloggen();
            }
        });
        //online inloggen met enter
        passwordField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                onlineInloggen();
            }
        });
        //offline/online inloggen met loginbutton
        loginButton.setOnAction(event -> {
            if(!getVbox().getChildren().contains(passwordField)){
                offlineInloggen();
            }
            else{
                onlineInloggen();
            }
        });
    }

    public void setTaal(){
        if(Settings.isNederlandseTaal){
        chatBot.setText("ChatBot42");
        loginButton.setText("Inloggen");
        emailField.setPromptText("Email:");
        passwordField.setPromptText("Wachtwoord:");
        register.setText("Heb je nog geen account? Registreer dan hier!");
        }
        else{
        chatBot.setText("ChatBot42");
        loginButton.setText("Sign in");
        emailField.setPromptText("Email:");
        passwordField.setPromptText("Password:");
        register.setText("Don't have an account yet? Sign up here!!");
        }
}

    public void setStijl(){
        if (Settings.isDarkMode){
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        root.setStyle("-fx-background-color: #232323");
        chatBot.setStyle("-fx-font-size: 50px; -fx-text-fill: #FFFFFF");
        emailField.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #232323;" +
                    "-fx-text-fill: #FFFFFF;");
        passwordField.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #232323;" +
                    "-fx-text-fill: #FFFFFF;");
        loginButton.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #232323;" +
                    "-fx-text-fill: #FFFFFF;");
        register.setStyle("-fx-text-fill: #FFFFFF;");
        }
        else{
        scene.getStylesheets().add(getClass().getResource("/styles-light.css").toExternalForm());
        root.setStyle("-fx-background-color: #FFFFFF");
        chatBot.setStyle("-fx-font-size: 50px; -fx-text-fill: #000000");
        emailField.setStyle("-fx-border-color: #000000;" +
                    "-fx-background-color: #CFD2D7;" +
                    "-fx-text-fill: #000000;");
        passwordField.setStyle("-fx-border-color: #000000;" +
                    "-fx-background-color: #CFD2D7;" +
                    "-fx-text-fill: #000000;");
        loginButton.setStyle("-fx-border-color: #000000;" +
                    "-fx-background-color: #CFD2D7;" +
                    "-fx-text-fill: #232323;");
        register.setStyle("-fx-text-fill: #232323;");}
}
    public void VboxLayout(){
        progressIndicator.setVisible(false);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        vbox.getChildren().addAll(imageView, chatBot, emailField, passwordField, loginButton, offlineButton,register, progressIndicator);
        vbox.setAlignment(Pos.CENTER);
        emailField.setMaxWidth(300);
        passwordField.setMaxWidth(300);
        register.setUnderline(true);

    }
    public void show() {
        VboxLayout();
        setTaal();
        setStijl();
        Event1();
        Event2();
        Event3();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean login(String email, String password) {
        Queries queries = new Queries(new SqlExecutor(new DatabaseConnection()));
        String encryptedPassword = SHA256Encryption.getSHA256EncryptedValue(password);
        ResultSet result = queries.Select(email, encryptedPassword);
        try {
            result.next();
            staticEmail = email;
            return result.getInt(1) == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void offlineInloggen(){
        Gebruiker offlineGebruiker = new Gebruiker(new OfflineInlogStrategy(emailField.getText(), primaryStage));
        offlineGebruiker.inloggen();
    }

    public void onlineInloggen(){
        Gebruiker onlineGebruiker = new Gebruiker(new OnlineInlogStrategy(primaryStage, emailField.getText(), passwordField.getText()));
        onlineGebruiker.inloggen();
    }
}