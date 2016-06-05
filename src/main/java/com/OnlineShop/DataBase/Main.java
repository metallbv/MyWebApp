package com.OnlineShop.DataBase;

import com.google.gson.JsonObject;
import com.mysql.cj.x.json.JsonParser;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by v.babiak on 05.06.2016.
 */
public class Main {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private final static String queryProductSelect = "select * from product";
    private final static String queryPurchaseSelect = "select * from purchase";
    private final static String queryProductInsert = "Insert into product VALUES(?,?,?)";
    private final static String queryPurchaseInsert = "Insert into purchase VALUES(?,?,?,?)";

    public static void main(String[] args) {

        final String queryGet = returnQueryGet();
        DBWorker dbWorker = new DBWorker();

        connection = dbWorker.getConnection();

        // Current data
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss", Locale.UK);
        String curDate = dateFormat.format(calendar.getTime());

        long curTime = System.currentTimeMillis() - 24 * 3600 * 1000;
        java.util.Date previosDate = calendar.getTime();
        previosDate.setTime(curTime);
        String previosDateString = dateFormat.format(calendar.getTime());

        // Write information from web app
        String str = null;
        String input = "данные полученные от сервера";

        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(input).getAsJsonObject();
        JsonArray pItem = mainObject.getAsJsonArray("p_item");

        for (JsonElement user : pItem) {

            JsonObject userObject = user.getAsJsonObject();
            userObject.get("p_id");
            str = userObject.get("p_id").toString();
        }

        // Get information about purchase
        preparedStatement = connection.prepareStatement(queryGet);
        preparedStatement.setString(1, previosDateString);
        preparedStatement.setString(2, curDate);
        ResultSet resultSetPurchase = preparedStatement.executeQuery();
        JSON json = new JSON();
        JSONArray ar = new JSONArray();
        while (resultSetPurchase.next()) {
            JsonObject resultJson = new JSONObject();
        }

        // Parserer JSON POST
        // Input purchase with current date
        preparedStatement = connection.prepareStatement(queryPurchaseInsert);
        preparedStatement.setInt(2, idproduct);
        preparedStatement.setInt(3, quntity);
        preparedStatement.setDate(4, curDate);
        preparedStatement.execute();


    }

    private static String returnQueryGet() {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select");
        queryBuilder.append("product.name,");
        queryBuilder.append("purchase.quantity,");
        queryBuilder.append("TRUNCATE(purchase.quantity*product.price, 2)");
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
