package com.example.chatbot42_project2_groep_7a.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries {

    private final SqlExecutor sqlExecutor;

    public Queries(SqlExecutor sqlEcecutor) {
        this.sqlExecutor = sqlEcecutor;
    }



    public ResultSet Select(String mainEmail, String mainPassword) {
        String sql = "SELECT count(1) FROM Account WHERE email = ? AND password = ?";
        return sqlExecutor.executeQuery(sql, mainEmail, mainPassword);
    }

    public ResultSet GetUID(String mainEmail) {
        String sql = "SELECT UID FROM Account WHERE email = ? LIMIT 1";
        return sqlExecutor.executeQuery(sql, mainEmail);
    }

    public ResultSet getWachtwoord(String UID) throws SQLException {
        String sql = "SELECT password FROM Account WHERE UID = ?";
        return sqlExecutor.executeQuery(sql, UID);
    }

    public ResultSet getEmail(String UID) throws SQLException {
        String sql = "SELECT email FROM Account WHERE UID = ?";
        return sqlExecutor.executeQuery(sql, UID);
    }

    public void Insert(String uid, String mainEmail, String mainPassword) {
        String encryptedPassword = SHA256Encryption.getSHA256EncryptedValue(mainPassword);
        String sql = "INSERT INTO Account (UID, email, password) VALUES (?, ?, ?)";
        sqlExecutor.executeQueryVoid(sql, uid, mainEmail, encryptedPassword);
    }

    public void DeleteAccount(String UID) {
        String sql = "DELETE FROM Account WHERE UID = ?";
        sqlExecutor.executeQueryVoid(sql, UID);
    }

    public void UpdateWachtwoord(String UID, String nieuwWachtwoord) {
        String encryptedPassword = SHA256Encryption.getSHA256EncryptedValue(nieuwWachtwoord);
        String sql = "UPDATE Account SET Password = ? WHERE UID = ?";
        sqlExecutor.executeQueryVoid(sql, encryptedPassword, UID);
    }

    public void UpdateEmail(String UID, String newEmail) {
        String sql = "UPDATE Account SET email = ? WHERE UID = ?";
        sqlExecutor.executeQueryVoid(sql, newEmail, UID);
    }

    public ResultSet getAdmin(String email) throws SQLException {
        String sql = "SELECT isAdmin FROM Account WHERE email = ?";
        return sqlExecutor.executeQuery(sql, email);
    }

}
