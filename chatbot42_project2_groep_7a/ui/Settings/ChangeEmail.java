package com.example.chatbot42_project2_groep_7a.ui.Settings;

import com.example.chatbot42_project2_groep_7a.ui.Login;
import com.example.chatbot42_project2_groep_7a.util.OnlineInlogStrategy;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import static com.example.chatbot42_project2_groep_7a.ui.Settings.Settings.stage;

public class ChangeEmail {
    private Stage primarystage;
    protected static Popup createEmailPopup = createEmailPopup(stage);
    Settings settings = new Settings(primarystage);

    public static Popup createEmailPopup(Stage primarystage) {
        Popup popup = new Popup();

        GridPane gridPane = createGridPane();
        Label titelLabel = createLabel("Change email");
        Label huidigLabel = createLabel("Current email:");
        Label nieuwLabel = createLabel("New email:");
        Label bevestigLabel = createLabel("Confirm email:");
        TextField huidigField = new TextField();
        TextField nieuwEmailField = new TextField();
        TextField bevestigField = new TextField();
        Button bevestigButton = createButton("Confirm Change", "Bevestig Wijziging");
        Button gaTerugButton = createButton("Cancel", "Ga Terug");

        bevestigButton.setOnAction(actionEvent -> {
            String huidigEmail = huidigField.getText();
            String nieuwEmail = nieuwEmailField.getText();
            String bevestigEmail = bevestigField.getText();

            if (EmailService.isEmailGelijk(nieuwEmail, bevestigEmail) &&
                    EmailService.bevestigEmailVerandering(huidigEmail, OnlineInlogStrategy.staticEmail)) {
                EmailService.updateGebruikersEmail(nieuwEmail, OnlineInlogStrategy.staticEmail);
                popup.hide();
                Login loginInterface = new Login(stage);
                loginInterface.show();
            } else {
                System.out.println("There was an error while verifying the email changes.");
            }
        });

        gaTerugButton.setOnAction(actionEvent -> popup.hide());

        gridPane.add(titelLabel, 0, 0, 2, 1);
        gridPane.add(huidigLabel, 0, 1);
        gridPane.add(nieuwLabel, 0, 2);
        gridPane.add(bevestigLabel, 0, 3);
        gridPane.add(huidigField, 1, 1);
        gridPane.add(nieuwEmailField, 1, 2);
        gridPane.add(bevestigField, 1, 3);
        gridPane.add(bevestigButton, 0, 4);
        gridPane.add(gaTerugButton, 1, 4);

        popup.getContent().add(gridPane);
        return popup;
    }

    private static GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle(getGridPaneStyle());
        return gridPane;
    }

    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle(getLabelStyle());
        return label;
    }

    private static Button createButton(String defaultText, String nlText) {
        Button button = new Button(Settings.isNederlandseTaal ? nlText : defaultText);
        return button;
    }

    private static String getLabelStyle() {
        if (Settings.isDarkMode) {
            return "-fx-padding: 10;" +
                    "-fx-background-color: #232323;" +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-size: 16px;";
        } else {
            return "-fx-padding: 10;" +
                    "-fx-background-color: #D2DFFA;" +
                    "-fx-text-fill: #000000;" +
                    "-fx-font-size: 16px;";
        }
    }

    private static String getGridPaneStyle() {
        return Settings.isDarkMode ? "-fx-background-color: #232323;" : "-fx-background-color: #D2DFFA;";
    }
}