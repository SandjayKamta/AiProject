package com.example.chatbot42_project2_groep_7a.ui.Settings;

import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.db.Queries;
import com.example.chatbot42_project2_groep_7a.db.SqlExecutor;
import com.example.chatbot42_project2_groep_7a.ui.Chat;
import com.example.chatbot42_project2_groep_7a.ui.Login;
import com.example.chatbot42_project2_groep_7a.ui.adminMenu;
import com.example.chatbot42_project2_groep_7a.ui.eventhandler;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.chatbot42_project2_groep_7a.util.OnlineInlogStrategy.isAdmin;

public class Settings implements eventhandler {

    protected Stage primaryStage;
    public static String UID;
    protected Button deleteAccount = new Button();
    protected Button wijzigInlogWachtwoord = new Button();
    protected Button wijzigEmail = new Button();
    protected Button gaTerug = new Button();
    protected Button darkMode = new Button();
    protected Button lightMode = new Button();
    private GridPane root = new GridPane();
    private Label account = new Label();
    private Label taal = new Label();
    private Label mode = new Label();
    protected Button taalEngels = new Button();
    protected Button taalNederlands = new Button();
    protected Button adminMenu = new Button();
    Scene scene = new Scene(root, 1280, 720);
    public static boolean isDarkMode = true;
    public static boolean isNederlandseTaal;

    private final Queries queries = new Queries(new SqlExecutor(new DatabaseConnection()));

    static Stage stage;


    public Settings(Stage primaryStage) {
        this.primaryStage = primaryStage;
        stage = primaryStage;
    }

    public void Event1(){
        deleteAccount.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            if(isNederlandseTaal){
                alert.setTitle("Verwijder account");
                alert.setHeaderText("Weet je zeker dat je, je account wilt verwijderen?");
                alert.setContentText("Deze actie kan niet ongedaan gemaakt worden.");

                ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

                if (result == ButtonType.OK) {
                    try {
                        deleteAccount();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Account verwijderd"); } }
            else{
                alert.setTitle("Delete Account");
                alert.setHeaderText("Are you sure you want to delete your account?");
                alert.setContentText("This cannot be reverted");

                ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

                if (result == ButtonType.OK) {
                    try {
                        deleteAccount();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Account verwijderd"); }
            }

        });
    }
    public void stijl(){
        scene.getStylesheets().clear();

        if (isDarkMode){
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles-settings.css")).toExternalForm());
        }
        else{
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles-settings-light.css")).toExternalForm());
        }
    }
    public void taal(){
        Locale currentLocale = Settings.isNederlandseTaal ? new Locale("nl") : Locale.ENGLISH;
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);

        account.setText(messages.getString("account"));
        mode.setText(messages.getString("mode"));
        taal.setText(messages.getString("taal"));
        gaTerug.setText(messages.getString("gaTerug"));
        taalNederlands.setText(messages.getString("taalNederlands"));
        taalEngels.setText(messages.getString("taalEngels"));
        lightMode.setText(messages.getString("lightMode"));
        darkMode.setText(messages.getString("darkMode"));
        deleteAccount.setText(messages.getString("deleteAccount"));
        wijzigEmail.setText(messages.getString("wijzigEmail"));
        wijzigInlogWachtwoord.setText(messages.getString("wijzigInlogWachtwoord"));
        adminMenu.setText(messages.getString("adminMenu"));
    }

    public void layout(){
        root.setHgap(35);
        root.setVgap(15);
        root.add(gaTerug, 0, 0);
        root.add(lightMode, 8,5);
        root.add(taalEngels, 14, 7);
        root.add(taalNederlands, 14, 5);
        root.add(wijzigInlogWachtwoord, 2, 7);
        root.add(wijzigEmail, 2, 9);
        root.add(darkMode,  8, 7);
        root.add(deleteAccount, 2, 5);
        root.add(taal, 14,4);
        taal.setUnderline(true);
        root.add(mode, 8, 4);
        mode.setUnderline(true);
        root.add(account,2,4 );
        account.setUnderline(true);
        root.getStyleClass().add("grid");
    }
    public void checkAdmin(){
        try{
            if(isAdmin()){
                root.add(adminMenu, 2, 11);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void alleKnoppen(){
        gaTerug.setOnAction(event -> Platform.runLater(() -> {
            Chat chatInterface = new Chat(primaryStage, Chat.account);
            chatInterface.show();
        }));

        darkMode.setOnAction(event -> {
            isDarkMode = true;
            stijl();
        });

        lightMode.setOnAction(event -> {
            isDarkMode = false;
            stijl();
        });

        taalNederlands.setOnAction(event ->{
            Settings.isNederlandseTaal = true;
            taal();
        });

        taalEngels.setOnAction(event -> {
            Settings.isNederlandseTaal = false;
            taal();
        });
        wijzigInlogWachtwoord.setOnAction(actionEvent ->
                ChangePassword.wachtwoordPopup.show(primaryStage));
        wijzigEmail.setOnAction(actionEvent ->
                ChangeEmail.createEmailPopup.show(primaryStage));

        adminMenu.setOnAction(actionEvent -> adminMenuInterface());
    }
    public void show() {
        layout();
        checkAdmin();
        alleKnoppen();
        Event1();
        stijl();
        taal();

        primaryStage.setTitle("ChatBot42");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void deleteAccount() throws SQLException {
        String UID = "";
        ResultSet result = queries.GetUID(Login.staticEmail);
        while (result.next()) {
            UID = result.getString("UID");
        }
        queries.DeleteAccount(UID);
        Login loginInterface = new Login(primaryStage);
        loginInterface.show();
    }


    private void adminMenuInterface(){
        com.example.chatbot42_project2_groep_7a.ui.adminMenu adminMenuInterface = new adminMenu(primaryStage);
        adminMenuInterface.show();
    }
}
