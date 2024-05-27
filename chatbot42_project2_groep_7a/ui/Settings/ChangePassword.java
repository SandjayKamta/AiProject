package com.example.chatbot42_project2_groep_7a.ui.Settings;

import com.example.chatbot42_project2_groep_7a.ui.Login;
import com.example.chatbot42_project2_groep_7a.util.OnlineInlogStrategy;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.chatbot42_project2_groep_7a.ui.Settings.Settings.*;

public class ChangePassword {
    private Stage primarystage;
    protected static Popup wachtwoordPopup = wachtwoordPopup(stage);
    Settings settings = new Settings(primarystage);

    public static Popup wachtwoordPopup(Stage primarystage) {
        Popup popup = new Popup();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        popup.setAutoHide(true);

        Label titelLabel = new Label("Change password");
        Label huidigLabel = new Label("Current password:");
        Label nieuwLabel = new Label("New password:");
        Label bevestigLabel = new Label("Confirm password:");
        if (isNederlandseTaal) {
            titelLabel.setText("Wijzig wachtwoord");
            huidigLabel.setText("Huidig wachtwoord:");
            nieuwLabel.setText("Nieuw wachtwoord:");
            bevestigLabel.setText("Bevestig wachtwoord:");
        }

        TextField huidigField = new TextField();
        TextField nieuwWachtwoordField = new TextField();
        TextField bevestigField = new TextField();

        Button bevestigButton = new Button("Confirm Change");
        Button gaTerugButton = new Button("Cancel");
        if (isNederlandseTaal) {
            bevestigButton.setText("Bevestig Wijziging");
            gaTerugButton.setText("Ga Terug");
        }

        bevestigButton.setOnAction(actionEvent -> {
            String huidigWachtwoord = huidigField.getText();
            String nieuwWachtwoord = nieuwWachtwoordField.getText();
            String bevestigWachtwoord = bevestigField.getText();

            if (PasswordService.isWachtwoordGelijk(nieuwWachtwoord, bevestigWachtwoord) &&
                    PasswordService.bevestigWachtwoordVerandering(huidigWachtwoord,  OnlineInlogStrategy.staticEmail) &&
                    nieuwWachtwoord.length() >= 8) {
                PasswordService.updateGebruikersWachtwoord(nieuwWachtwoord, OnlineInlogStrategy.staticEmail);
                popup.hide();
                Login loginInterface = new Login(stage);
                loginInterface.show();
            } else {
                System.out.println("There was an error while verifying the password changes.");
            }
        });

        gaTerugButton.setOnAction(actionEvent -> popup.hide());

        gridPane.add(titelLabel, 0, 0, 2, 1);
        gridPane.add(huidigLabel, 0, 1);
        gridPane.add(nieuwLabel, 0, 2);
        gridPane.add(bevestigLabel, 0, 3);
        gridPane.add(huidigField, 1, 1);
        gridPane.add(nieuwWachtwoordField, 1, 2);
        gridPane.add(bevestigField, 1, 3);
        gridPane.add(bevestigButton, 0, 4);
        gridPane.add(gaTerugButton, 1, 4);
        gridPane.getStyleClass().add("grid");
        if (isDarkMode) {
            gridPane.getStylesheets().add(Objects.requireNonNull(ChangePassword.class.getResource("/styles-password.css")).toExternalForm());
        } else {
            gridPane.getStylesheets().add(Objects.requireNonNull(ChangePassword.class.getResource("/styles-password-light.css")).toExternalForm());
        }
        popup.getContent().add(gridPane);
        return popup;
    }



}
