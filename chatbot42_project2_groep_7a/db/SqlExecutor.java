package com.example.chatbot42_project2_groep_7a.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExecutor {

    private final DatabaseConnection databaseConnection;

    public SqlExecutor(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    //this method is used to execute a query
    //it takes in a string and an array of objects
    //the string is the query
    //the objects are the parameters
    public ResultSet executeQuery(String sql, Object... parameters) {
        try {
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, parameters);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeQueryVoid(String sql, Object... parameters) {
        try {
            Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setParameters(preparedStatement, parameters);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to set the parameters of a query
    //it takes in a prepared statement and an array of objects
    //the prepared statement is the query
    //the objects are the parameters
    private void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }
}
