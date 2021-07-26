package com.example.jihyun_myorder.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderDAO {

    @Query("SELECT * FROM tbl_order ORDER BY order_id DESC")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM tbl_order WHERE order_id = :id")
    Order getOrderById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);


}
