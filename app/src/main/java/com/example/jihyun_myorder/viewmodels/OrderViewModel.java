package com.example.jihyun_myorder.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jihyun_myorder.database.Order;
import com.example.jihyun_myorder.repositories.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private LiveData<List<Order>> allOrders;

    public OrderViewModel(Application application) {
        super(application);
        this.orderRepository = new OrderRepository(application);
        this.allOrders = this.orderRepository.allOrders;
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public Order getOrderById(long id) {
        return this.orderRepository.getOrderById(id);
    }

    public void insertOrder(Order newOrder) {
        this.orderRepository.insertOrder(newOrder);
    }

    public void updateOrder(Order order) {
        this.orderRepository.updateOrder(order);
    }

    public void deleteOrder(Order order) {
        this.orderRepository.deleteOrder(order);
    }
}
