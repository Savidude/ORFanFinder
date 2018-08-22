package com.orfangenes.control.dabases;

import com.orfangenes.constants.Database;

import java.sql.*;

public class ORFanDB {

    public static Connection connectToDatabase (String database) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + Database.HOST + ":" + Database.PORT + "/" +
                    database, Database.USERNAME, Database.PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void insertRecordPreparedStatement (Connection connection, String query, Object[] data) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < data.length; i++) {
                if (data[i] instanceof Integer) {
                    preparedStatement.setInt(i, (int)data[i]);
                } else if (data[i] instanceof String) {
                    preparedStatement.setString(i, (String)data[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean recordExists (Connection connection, String tableName, String sequence) {
        String selectQuery = "SELECT sequence FROM " + tableName + " WHERE sequence = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, sequence);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
