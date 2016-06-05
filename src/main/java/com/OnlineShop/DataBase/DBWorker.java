package com.OnlineShop.DataBase;

import java.sql.*;

/**
 * Created by v.babiak on 04.06.2016.
 */
public class DBWorker {

    private static final String URL = "jdbc:mysql://localhost:3306/onlineshopdb";
    private static final String USERNAME = "admin";
    private static final String PUSSWORD = "Pswtrain123";

    private Connection connection;

    public DBWorker() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PUSSWORD);

        } catch (SQLException e) {
            System.out.println("Не удалось получить соединение с БД! " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
