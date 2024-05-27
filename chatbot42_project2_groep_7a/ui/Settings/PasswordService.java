package com.example.chatbot42_project2_groep_7a.ui.Settings;

import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.db.Queries;
import com.example.chatbot42_project2_groep_7a.db.SHA256Encryption;
import com.example.chatbot42_project2_groep_7a.db.SqlExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordService {

    private static final Queries queries = new Queries(new SqlExecutor(new DatabaseConnection()));

    public static boolean bevestigWachtwoordVerandering(String huidigWachtwoord, String email) {
        try {
            String UID = "";
            ResultSet uidResult = queries.GetUID(email);
            if (uidResult.next()) {
                UID = uidResult.getString("UID");
            }

            ResultSet wachtwoordResult = queries.getWachtwoord(UID);
            if (wachtwoordResult.next()) {
                String hashedPassword = wachtwoordResult.getString("password");
                String hashedCurrentPassword = SHA256Encryption.getSHA256EncryptedValue(huidigWachtwoord);
                return hashedCurrentPassword.equals(hashedPassword);
            } else {
                System.out.println("No password found in database for the specified UID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void updateGebruikersWachtwoord(String nieuwWachtwoord, String email){
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
            queries.UpdateWachtwoord(UID, nieuwWachtwoord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isWachtwoordGelijk(String nieuwWachtwoord, String bevestigWachtwoord) {
        return nieuwWachtwoord.equals(bevestigWachtwoord);
    }
}