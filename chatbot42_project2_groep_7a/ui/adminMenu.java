package com.example.chatbot42_project2_groep_7a.ui;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class adminMenu {
    private GridPane root = new GridPane();
    private TableView tableView = new TableView();
    private Scene scene = new Scene(root, 1280, 720);
    protected Stage primaryStage;
    public adminMenu(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void show(){
        root.add(tableView, 1,1);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
