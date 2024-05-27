package com.example.chatbot42_project2_groep_7a.util;

public class Gebruiker {

    final InlogStrategy inlogStategy;

    public Gebruiker(InlogStrategy inlogStrategy){
        this.inlogStategy = inlogStrategy;
    }

    public void inloggen(){
        inlogStategy.inloggen();
    }
}