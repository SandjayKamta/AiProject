package com.example.chatbot42_project2_groep_7a.ui;

import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.db.Queries;
import com.example.chatbot42_project2_groep_7a.db.SqlExecutor;
import com.example.chatbot42_project2_groep_7a.ui.Settings.Settings;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public class Register implements eventhandler {
    private Stage primaryStage;
    private ProgressIndicator progressIndicator;
    private TextField textField;
    private PasswordField passwordField;
    private PasswordField passwordFieldCheck;
    private Image image =  new Image(("DALL_E_2023-05-22_15.05.24_-_app_icon_for_chatbot_42-removebg-preview.png"));
    private ImageView imageView = new ImageView(image);
    private Button btn = new Button();
    private Label login = new Label();
    private VBox vBox = new VBox();
    private BorderPane root = new BorderPane(vBox);
    private Scene scene = new Scene(root, 1280, 720);

    public Register(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.progressIndicator = new ProgressIndicator();
        this.textField = new TextField();
        this.passwordField = new PasswordField();
        this.passwordFieldCheck = new PasswordField();
    }

    public void show() {
        Vbox();
        Taal();
        Stijl();
        Event1();
        Event2();

        primaryStage.setScene(scene);
    }

    public void Vbox(){
        vBox.getChildren().addAll(textField, passwordField, passwordFieldCheck, btn, login, progressIndicator);
        progressIndicator.setVisible(false);
        imageView.setFitHeight(200);
        imageView.setFitWidth(100);


        vBox.setAlignment(Pos.CENTER);
        textField.setMaxWidth(300);
        passwordField.setMaxWidth(300);
        passwordFieldCheck.setMaxWidth(300);

    }
    public void Stijl(){
        if (Settings.isDarkMode) {
            root.setStyle("-fx-background-color: #232323;");
            textField.setStyle(
                    "-fx-border-color: #385CA9;" +
                            "-fx-background-color: #232323;" +
                            "-fx-text-fill: #FFFFFF;");
            passwordFieldCheck.setStyle(
                    "-fx-border-color: #385CA9;" +
                            "-fx-background-color: #232323;" +
                            "-fx-text-fill: #FFFFFF;");
            passwordField.setStyle(
                    "-fx-border-color: #385CA9;" +
                            "-fx-background-color: #232323;" +
                            "-fx-text-fill: #FFFFFF;");
            btn.setStyle(
                    "-fx-border-color: #385CA9;" +
                            "-fx-background-color: #232323;" +
                            "-fx-text-fill: #FFFFFF");
            login.setStyle(
                    "-fx-background-color: #232323;" +
                            "-fx-text-fill: #FFFFFF"); }
        else{
            root.setStyle("-fx-background-color: #FFFFFF;");
            textField.setStyle(
                    "-fx-border-color: #000000;" +
                            "-fx-background-color: #CFD2D7;" +
                            "-fx-text-fill: #000000;");
            passwordFieldCheck.setStyle(
                    "-fx-border-color: #000000;" +
                            "-fx-background-color: #CFD2D7;" +
                            "-fx-text-fill: #000000;");
            passwordField.setStyle(
                    "-fx-border-color: #000000;" +
                            "-fx-background-color: #CFD2D7;" +
                            "-fx-text-fill: #000000;"
            );
            btn.setStyle(
                    "-fx-border-color: #000000;" +
                            "-fx-background-color: #CFD2D7;" +
                            "-fx-text-fill: #000000;");
            login.setStyle(
                    "-fx-background-color: #FFFFFF;" +
                            "-fx-text-fill: #000000;");

        }
    }
    public void Taal(){
        if(Settings.isNederlandseTaal){
            textField.setPromptText("Email: ");
            passwordField.setPromptText("Wachtwoord: ");
            passwordFieldCheck.setPromptText("Herhaal wachtwoord: ");
            btn.setText("Registreer");
            login.setText("Heb je al een account? Log dan hier in!");
        }
        else {
            textField.setPromptText("Email: ");
            passwordField.setPromptText("Password: ");
            passwordFieldCheck.setPromptText("Repeat password: ");
            btn.setText("Sign up");
            login.setText("Already have an account? Sign in here!");
        }
    }

    @Override
    public void Event1() {
        btn.setOnAction(event -> {
            progressIndicator.setVisible(true);

            new Thread(() -> {
                try {
                    if (registerAccount(textField.getText(), passwordField.getText())) {
                        Platform.runLater(() -> {
                            Login loginInterface = new Login(primaryStage);
                            loginInterface.show();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressIndicator.setVisible(false);
            }).start();
        });
    }
    public void Event2(){
        login.setUnderline(true);
        login.setOnMouseClicked(event -> {
            Login loginInterface = new Login(primaryStage);
            loginInterface.show();
            System.out.println("login");
        });
    }

    public boolean registerAccount(String email, String password) {
        Queries queries = new Queries(new SqlExecutor(new DatabaseConnection()));
        UUID uid = UUID.randomUUID();
        queries.Insert(uid.toString() ,email, password);
        return true;
    }
}