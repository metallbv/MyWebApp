package com.OnlineShop.DataBase;

import java.util.Date;

/**
 * Created by v.babiak on 05.06.2016.
 */
public class Purchase {

    private int id;
    private int idProduct;
    private int quantity;
    private Date purchaseDate;

    public Purchase() {

    }

    public Purchase(int idProduct, int quantity, Date purchaseDate) {

        this.idProduct = idProduct;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public Purchase(int id, int idProduct, int quantity, Date purchaseDate) {
        this.id = id;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", idProduct='" + idProduct + '\'' +
                ", quantity='" + quantity + '\'' +
                ", purchaseDate=" + purchaseDate +
                '}';
    }

}
