package com.example.jihyun_myorder.models;

import android.os.Parcel;
import android.os.Parcelable;

// Jihyun Lee, 141859181
public class OrderItem {

    private String coffeeType;
    private String size;
    private int quantity;
    private long orderID;

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "coffeeType='" + coffeeType + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
