package com.example.chatbot42_project2_groep_7a.util;
import com.example.chatbot42_project2_groep_7a.ui.Settings.Settings;
import javafx.scene.control.Alert;

public abstract class AbstractInlog implements InlogStrategy{

    public void foutmelding(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if(Settings.isNederlandseTaal){
            alert.setTitle("Foutmelding");
            alert.setHeaderText("Inloggen is niet gelukt");
            alert.setContentText("Inloggegevens kloppen niet"); }
        else {
            alert.setTitle("Error");
            alert.setHeaderText("Signing in was onsuccesfull");
            alert.setContentText("Login information is incorrect");}
        alert.showAndWait();
    }
}
