package com.example.chatbot42_project2_groep_7a.util;
import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.db.Queries;
import com.example.chatbot42_project2_groep_7a.db.SHA256Encryption;
import com.example.chatbot42_project2_groep_7a.db.SqlExecutor;
import com.example.chatbot42_project2_groep_7a.ui.Chat;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OnlineInlogStrategy extends AbstractInlog {
    private final Stage primarystage;
    private final String email;
    public static String staticEmail;
    private String password;

    public OnlineInlogStrategy(Stage primarystage, String email,String password){
        this.primarystage = primarystage;
        this.email = email;
        this.staticEmail= email;
        this.password = password;
    }
    @Override
    public void inloggen() {
        boolean loggedIn = login(email, password);
        if (loggedIn) {
            Chat chat = new Chat(primarystage , email);
            chat.show();
            System.out.println("succesvol online ingelogd");
        } else {
            foutmelding();
        }
    }
    public boolean login(String email, String password) {
        Queries queries = new Queries(new SqlExecutor(new DatabaseConnection()));
        String encryptedPassword = SHA256Encryption.getSHA256EncryptedValue(password);
        ResultSet result = queries.Select(email, encryptedPassword);
        try {
            result.next();
            staticEmail= email;
            return result.getInt(1) == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static boolean isAdmin() throws SQLException {
        if(staticEmail!= null){
            Queries queries = new Queries(new SqlExecutor(new DatabaseConnection()));
            ResultSet isAdmin = queries.getAdmin(staticEmail);
            isAdmin.next();
            return isAdmin.getBoolean("isAdmin");
        }
        return false;
    }
}