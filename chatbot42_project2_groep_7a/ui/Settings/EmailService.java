package com.example.chatbot42_project2_groep_7a.ui.Settings;

import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.db.Queries;
import com.example.chatbot42_project2_groep_7a.db.SqlExecutor;
import com.example.chatbot42_project2_groep_7a.ui.Login;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailService {

    private static final Queries queries = new Queries(new SqlExecutor(new DatabaseConnection()));

    public static boolean isEmailGelijk(String nieuwEmail, String bevestigEmail) {
        return nieuwEmail.equals(bevestigEmail);
    }

    public static boolean bevestigEmailVerandering(String huidigEmail, String email) {
        try {
            String UID = "";
            ResultSet uidResult = queries.GetUID(email);
            uidResult.next();
            UID = uidResult.getString("UID");



            ResultSet emailResult = queries.getEmail(UID);
            if (emailResult.next()) {
                String currentEmail = emailResult.getString("email");
                return huidigEmail.equals(currentEmail);
            } else {
                System.out.println("No email found in database for the specified UID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void updateGebruikersEmail(String nieuwEmail, String email) {
        String UID = "";
        try {
            ResultSet result = queries.GetUID(email);
            while (result.next()) {
                UID = result.getString("UID");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            queries.UpdateEmail(UID, nieuwEmail);
            Login.staticEmail = nieuwEmail;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}