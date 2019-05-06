package com.example.jovan.pizzaproject.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jovan.pizzaproject.AdapterComponents.OrderAdapter;
import com.example.jovan.pizzaproject.AdapterComponents.RecyclerViewAdapter;
import com.example.jovan.pizzaproject.Models.Order;
import com.example.jovan.pizzaproject.Persistence.Repository.OrderRepository;
import com.example.jovan.pizzaproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MyOrderForm extends AppCompatActivity {

    TextView totalPrice;
    OrderRepository orderRepository;
    List<Order> orders;

    RecyclerView recyclerView;
    OrderAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    AppCompatButton placeorder;
    int total;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_form);


        init();
        LoadOrders();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Log.d("ORDER", orders.get(viewHolder.getAdapterPosition()).getProductName());
                orderRepository.delete(orders.get(viewHolder.getAdapterPosition()));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }).attachToRecyclerView(recyclerView);


    }




    private void updatePrice() {
        total = 0;
        for(Order o : orders){
            total += Integer.parseInt(o.getPrice());
        }
        try {
            totalPrice.setText(total);
        }catch (Exception ex){
            totalPrice.setText(ex.getMessage());
        }
    }

    private void initAdapterComponents() {
        orderRepository = new OrderRepository(getApplicationContext());
        orders = new ArrayList<>();
        recyclerView = findViewById(R.id.listCart);
        placeorder=findViewById(R.id.placeOrder);
        adapter = new OrderAdapter(MyOrderForm.this, orders);
        linearLayoutManager = new LinearLayoutManager(MyOrderForm.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        total = 0;
        totalPrice.setText(String.valueOf(total));
    }

    private void init() {
        totalPrice = findViewById(R.id.total);
        initAdapterComponents();



        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orders.size() == 0){
                    Toast.makeText(MyOrderForm.this, "Немате ниту една нарачка во кошничката!", Toast.LENGTH_LONG).show();
                }else{
                    goToPay();
                }
            }
        });
    }

    private void goToPay() {
        Intent intent = new Intent(this ,PayActivity.class);
        intent.putExtra("totalPay",String.valueOf(total));
        startActivity(intent);
    }

    private void LoadOrders() {
        try{
            orderRepository.listAllOrders().observe(MyOrderForm.this,
                    new Observer<List<Order>>() {
                        @Override
                        public void onChanged(@Nullable List<Order> orders1) {
                            for(Order order : orders1){
                                orders.add(order);
                                Log.d("ORDERS", order.getProductName());
                                total = total + (Integer.parseInt(order.getPrice()) * Integer.parseInt(order.getQuantity()));
                                Log.d("PRICE", String.valueOf(total));
                            }
                            adapter.updateData(orders);
                            totalPrice.setText(String.format(" %s ",String.valueOf(total)));
                        }
                    });
        }catch (Exception ex){
            Log.d("ORDER", "Greska u citanje od bazata : " + ex.getMessage());
        }
    }
}
