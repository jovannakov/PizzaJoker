package com.example.jovan.pizzaproject.Persistence;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.jovan.pizzaproject.Models.Order;
import com.example.jovan.pizzaproject.Persistence.Dao.OrderDao;

@Database(entities = {Order.class}, version = 1, exportSchema = false)
public abstract class DatabaseOrders extends RoomDatabase {

    public abstract OrderDao daoAccess();

    
}
