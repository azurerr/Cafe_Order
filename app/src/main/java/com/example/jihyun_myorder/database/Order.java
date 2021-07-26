package com.example.jihyun_myorder.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_order")
public class Order {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private long orderID;

    @NonNull
    @ColumnInfo(name = "order_coffee_type")
    private String coffeeType;

    @NonNull
    @ColumnInfo(name = "order_size")
    private String size;

    @NonNull
    @ColumnInfo(name = "order_quantity")
    private int quantity;

    public Order() {
    }

    public Order(long orderID, String coffeeType, String size, int quantity) {
        this.orderID = orderID;
        this.coffeeType = coffeeType;
        this.size = size;
        this.quantity = quantity;
    }

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
                "orderID=" + orderID +
                ", coffeeType='" + coffeeType + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
