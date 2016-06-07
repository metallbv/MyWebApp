package com.OnlineShop.DataBase;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by v.babiak on 05.06.2016.
 */
public class DBMain {

    private static DBWorker dbWorker = new DBWorker();
    private static Connection connection = dbWorker.getConnection();
    private static PreparedStatement preparedStatement = null;
    private final static String getQueryProductId = "SELECT idProduct FROM product Where name = ?";
    private final static String queryProductInsert = "Insert into product (name, price) VALUES(?,?)";
    private final static String queryPurchaseInsert = "Insert into purchase (idProduct, quantity, purchasedate) VALUES(?,?,?)";
    private final static String queryGet = returnQueryGet();

    // Create new Purchase
    public static void insertPurchase(WebData webData, Integer idProduct) {

        try {
            preparedStatement = connection.prepareStatement(queryPurchaseInsert);
            preparedStatement.setInt(1, idProduct);
            preparedStatement.setInt(2, webData.getCount());
            preparedStatement.setString(3, LocalDate.now().toString());
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Create new Product
    public static Integer insertProduct(WebData webData) {

        Integer idProduct = null;
        try {
            preparedStatement = connection.prepareStatement(queryProductInsert);
            preparedStatement.setString(1, webData.getName());
            preparedStatement.setFloat(2, webData.getSum() / webData.getCount());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(getQueryProductId);
            preparedStatement.setString(1, webData.getName());
            ResultSet resultSetProduct = preparedStatement.executeQuery();

            if (resultSetProduct.next()) {
                idProduct = resultSetProduct.getInt("idProduct");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return idProduct;
    }

    // Handle data from method post
    public static void handleWebData(WebData webData) {

        try {
            preparedStatement = connection.prepareStatement(getQueryProductId);
            preparedStatement.setString(1, webData.getName());
            ResultSet resultSetProduct = preparedStatement.executeQuery();

            if (resultSetProduct.next()) {
                insertPurchase(webData, resultSetProduct.getInt("idProduct"));
            } else {
                int idProduct = insertProduct(webData);
                insertPurchase(webData, idProduct);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Get WebData for period
    public static ArrayList<WebData> getWebData(Integer period) {

        ArrayList<WebData> webDatas = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        LocalDate previosDate = localDate.minusMonths(6);

        try {
            preparedStatement = connection.prepareStatement(queryGet);
            preparedStatement.setString(1, previosDate.toString());
            preparedStatement.setString(2, localDate.toString());
            ResultSet resultSetPurchase = preparedStatement.executeQuery();

            while (resultSetPurchase.next()) {
                WebData webData = new WebData();
                webData.setName(resultSetPurchase.getString("name"));
                webData.setCount(resultSetPurchase.getInt("quantity"));
                webData.setSum(resultSetPurchase.getFloat("sum"));
                webDatas.add(webData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return webDatas;

    }

    // Get text query
    private static String returnQueryGet() {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select ");
        queryBuilder.append("product.name, ");
        queryBuilder.append("purchase.quantity, ");
        queryBuilder.append("TRUNCATE(purchase.quantity*product.price, 2) as sum ");
        queryBuilder.append("from purchase ");
        queryBuilder.append("INNER JOIN product on purchase.idproduct = product.idProduct ");
        queryBuilder.append("where purchasedate between ? and ?");

        return queryBuilder.toString();

    }

}
