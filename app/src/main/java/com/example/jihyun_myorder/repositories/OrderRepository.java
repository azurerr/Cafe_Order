package com.example.jihyun_myorder.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.jihyun_myorder.database.AppDB;
import com.example.jihyun_myorder.database.Order;
import com.example.jihyun_myorder.database.OrderDAO;

import java.util.List;

public class OrderRepository {
    private AppDB db;
    private OrderDAO orderDAO;
    public LiveData<List<Order>> allOrders;

    public OrderRepository(Application application) {
        this.db = AppDB.getDatabase(application);
        this.orderDAO = this.db.orderDAO();
        this.allOrders = this.orderDAO.getAllOrders();
    }

    public void insertOrder(Order newOrder) {
        AppDB.databaseWriteExecutor.execute(() -> {
            orderDAO.insert(newOrder);
        });
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public Order getOrderById(long id) {
        return orderDAO.getOrderById(id);
    }

    public void updateOrder(Order order) {
        AppDB.databaseWriteExecutor.execute(() -> {
            this.orderDAO.update(order);
        });
    }
    public void deleteOrder(Order order) {
        AppDB.databaseWriteExecutor.execute(() -> {
            this.orderDAO.delete(order);
        });
    }

}
