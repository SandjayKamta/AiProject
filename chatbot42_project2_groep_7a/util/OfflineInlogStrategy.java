package com.example.chatbot42_project2_groep_7a.util;
import com.example.chatbot42_project2_groep_7a.ui.Chat;
import javafx.stage.Stage;
import java.util.ArrayList;

public class OfflineInlogStrategy extends AbstractInlog {
    private final ArrayList<String> offlineGebruikers = new ArrayList<>();

    private final Stage primarystage;
    private final String email;

    public OfflineInlogStrategy(String email, Stage primarystage){
        this.email = email;
        this.primarystage = primarystage;
    }
    @Override
    public void inloggen() {
        setOfflineGebruikers();
        if(checkGebruiker()){
            logIn();
        }
        else{
            foutmelding();
        }
    }
    public void setOfflineGebruikers(){
        offlineGebruikers.add("offlinetest");
    }
    public boolean checkGebruiker(){
        for(String gebruiker: offlineGebruikers){
            if(gebruiker.equals(email)){
                return true;
            }
        }
        return false;
    }
    public void logIn(){
        Chat chat = new Chat(primarystage , email);
        chat.show();
        System.out.println("succesvol offline ingelogd");
    }
}