package com.example.jovan.pizzaproject.Persistence.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import com.example.jovan.pizzaproject.Models.Order;
import com.example.jovan.pizzaproject.Persistence.DatabaseOrders;

import java.util.List;

public class OrderRepository {
    private static String DB_NAME = "OrderDatabase";

    private DatabaseOrders orderDatabase;
    private Context mContext;

    public OrderRepository(Context context){
        orderDatabase = Room.databaseBuilder(context,
                DatabaseOrders.class,
                DB_NAME)
                .build();
    }


    // inserting item
    public void insertItem(final Order order){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    orderDatabase.daoAccess().insertTask(order);
                }catch (Exception e){
                }
                return null;
            }
        }.execute();

    }


    // listing all of the orders
    public LiveData<List<Order>> listAllOrders(){
        return orderDatabase.daoAccess().fetchAllTasks();
    }


    // deleting the whole table of orders
    public void deleteAll(){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                orderDatabase.daoAccess().deleteAll();
                return null;
            }
        }.execute();
    }

    public void deleteById(String id){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                orderDatabase.daoAccess().deleteID(id);
                return null;
            }
        }.execute();
    }

    public void update(Order o){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                orderDatabase.daoAccess().update(o);
                return null;
            }
        }.execute();
    }

    public void updateSpecific(String q, String id){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                orderDatabase.daoAccess().updateSpec(q, id);
                return null;
            }
        }.execute();
    }

    public void delete(Order order){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                orderDatabase.daoAccess().delete(order);
                return null;
            }
        }.execute();
    }
}
