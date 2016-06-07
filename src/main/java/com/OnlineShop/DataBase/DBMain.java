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
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private final static String queryProductSelect = "select * from product";
    private final static String getQueryProductId = "SELECT idProduct FROM product Where name = ?";
    private final static String queryPurchaseSelect = "select * from purchase";
    private final static String queryProductInsert = "Insert into product (name, price) VALUES(?,?)";
    private final static String queryPurchaseInsert = "Insert into purchase (idProduct, quantity, purchasedate) VALUES(?,?,?)";
    private final static String queryGet = returnQueryGet();

    /*public static void main(String[] args) {

        ArrayList<WebData> webDatas = DBMain.getWebData(1);
        for (WebData webData : webDatas) {
            System.out.println(webData);
        }


        // Write information from web app
        String str = null;
        String input = "данные полученные от сервера";
        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(input).getAsJsonObject();
        JsonArray pItem = mainObject.getAsJsonArray("p_item");

        for (JsonElement data : pItem) {

            JsonObject productObject = data.getAsJsonObject();
            productObject.get("product");
            productObject.get("count");
            productObject.get("sum");
        }*/

    // Get information about purchase
        /*

        // Parserer JSON POST
        // Input purchase with current date
        try {
            preparedStatement = connection.prepareStatement(queryPurchaseInsert);
            preparedStatement.setInt(2, idproduct);
            preparedStatement.setInt(3, quntity);
            preparedStatement.setDate(4, curDate);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }*/

    public static void insertPurchase(WebData webData, Integer idProduct) {

        connection = dbWorker.getConnection();
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

    public static Integer insertProduct(WebData webData) {
        connection = dbWorker.getConnection();
        Integer idProduct = null;
        try {
            preparedStatement = connection.prepareStatement(queryProductInsert);
            preparedStatement.setString(1, webData.getName());
            preparedStatement.setFloat(2, webData.getSum()/webData.getCount());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(getQueryProductId);
            preparedStatement.setString(1, webData.getName());
            ResultSet resultSetProduct = preparedStatement.executeQuery();

            if (resultSetProduct.next()) {
                idProduct =  resultSetProduct.getInt("idProduct");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return idProduct;
    }

    public static void handleWebData(WebData webData) {

        connection = dbWorker.getConnection();
        try {
            preparedStatement = connection.prepareStatement(getQueryProductId);
            preparedStatement.setString(1, webData.getName());
            ResultSet resultSetProduct = preparedStatement.executeQuery();

            if (resultSetProduct.next()) {
                insertPurchase(webData, resultSetProduct.getInt("idProduct"));
            } else {
                int idProduct = insertProduct(webData);
                insertPurchase(webData, resultSetProduct.getInt("idProduct"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Get WebData for period
    public static ArrayList<WebData> getWebData(Integer period) {

        connection = dbWorker.getConnection();

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
        queryBuilder.append("select");
        queryBuilder.append("product.name,");
        queryBuilder.append("purchase.quantity,");
        queryBuilder.append("TRUNCATE(purchase.quantity*product.price, 2) as sum");
        queryBuilder.append("from purchase");
        queryBuilder.append("INNER JOIN product on purchase.idproduct = product.idProduct");
        queryBuilder.append("where purchasedate between ? and ?");

        return queryBuilder.toString();

    }

    // Get products
    private ArrayList<Product> getArrayListProduct() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryProductSelect);
            ResultSet resultProducts = preparedStatement.executeQuery();
            while (resultProducts.next()) {
                Product product = new Product();
                product.setId(resultProducts.getInt("idProduct"));
                product.setName(resultProducts.getString("name"));
                product.setPrice(resultProducts.getFloat("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return products;
    }

    // Get purchase
    private ArrayList<Purchase> getArrayListPurchase() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryPurchaseSelect);
            ResultSet resultPurchase = preparedStatement.executeQuery();
            while (resultPurchase.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(resultPurchase.getInt("id"));
                purchase.setIdProduct(resultPurchase.getInt("idProduct"));
                purchase.setQuantity(resultPurchase.getInt("quantity"));
                purchase.setPurchaseDate(resultPurchase.getDate("purchaseDate"));
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return purchases;
    }

}
