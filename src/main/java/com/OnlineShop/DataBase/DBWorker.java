package com.OnlineShop.DataBase;

import java.sql.*;

/**
 * Created by v.babiak on 04.06.2016.
 */
public class DBWorker {

    private static final String URL = "jdbc:mysql://localhost/onlineshopdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "admin";
    private static final String PUSSWORD = "root";

    private static Connection connection = null;

    public DBWorker() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager
                    .getConnection(URL, USERNAME, PUSSWORD);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

    }

    public static Connection getConnection() {
        return connection;
    }

}
