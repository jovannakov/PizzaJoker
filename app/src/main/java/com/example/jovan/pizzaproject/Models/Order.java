package com.example.jovan.pizzaproject.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

@Entity (tableName = "Order")
public class Order implements Serializable {

    @PrimaryKey @ColumnInfo(name = "productID") @NonNull
    private String productID;

    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "Quantity")
    private String Quantity;

    @ColumnInfo(name = "Price")
    private String Price;


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
