package com.example.jovan.pizzaproject.Persistence.Dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.example.jovan.pizzaproject.Models.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    Long insertTask(Order order);


    @Query("SELECT * FROM `Order`")
    LiveData<List<Order>> fetchAllTasks();

    @Query("DELETE FROM `Order`")
    void deleteAll();

    @Query("DELETE FROM `Order` WHERE productID = :id")
    void deleteID(String id);

    @Delete
    void delete(Order order);

    @Query("UPDATE `Order` SET Quantity = :q WHERE productID = :id")
    void updateSpec(String q, String id);

    @Update
    void update(Order order);
}
