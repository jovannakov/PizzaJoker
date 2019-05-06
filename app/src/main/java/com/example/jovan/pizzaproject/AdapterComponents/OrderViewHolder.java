package com.example.jovan.pizzaproject.AdapterComponents;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jovan.pizzaproject.Models.Order;
import com.example.jovan.pizzaproject.Persistence.Repository.OrderRepository;
import com.example.jovan.pizzaproject.R;
import com.example.jovan.pizzaproject.UI.MyOrderForm;

import java.util.ArrayList;
import java.util.List;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    TextView txtView;
    View parent;

    OrderRepository orderRepository;
    Order order;
    OrderAdapter adapter;
    List<Order> orders;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        initUI(itemView);
    }

    private void initUI(View v) {
        txtView = v.findViewById(R.id.dataPizza);
        orderRepository = new OrderRepository(v.getContext());
        orders = new ArrayList<>();
        adapter = new OrderAdapter(v.getContext(), orders);

    }




    public void bind(Order order){
        txtView.setText(String.format("%s - %s", order.getProductName(), order.getQuantity()));
    }

    public View getParent(){
        return parent;
    }
    public void setParent(View parent){
        this.parent = parent;
    }


}
