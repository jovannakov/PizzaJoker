package com.example.jovan.pizzaproject.UI;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.example.jovan.pizzaproject.Models.Order;
import com.example.jovan.pizzaproject.Models.Pizza;
import com.example.jovan.pizzaproject.Persistence.Repository.OrderRepository;
import com.example.jovan.pizzaproject.R;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PizzaDetails extends AppCompatActivity {
    String id;
    Pizza pizza;
    ImageView imgView;
    TextView pizzaName, pizzaIngredients, pizzaPrice, txtQunatity;
    FirebaseDatabase db;
    DatabaseReference dbRef;
    FloatingActionButton btnCart;
    OrderRepository orderRepository;
    List<Order> listOrder;
    Order order;
    int quantity;
    int total;

    Button btnMinus, btnPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_details);

        init();
        initButtons();
        initDatabase();
        LoadPizza();


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

    }

    private void initDatabase() {
        orderRepository = new OrderRepository(getApplicationContext());
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();
    }

    private void init() {
        quantity = 1;
        id = getIntent().getExtras().getString("id");

        imgView = findViewById(R.id.image_pizza);
        pizzaName = findViewById(R.id.pizzaName);
        pizzaPrice = findViewById(R.id.pizzaPrice);
        pizzaIngredients = findViewById(R.id.ingredients);
        btnCart = findViewById(R.id.btnCart);


        txtQunatity = findViewById(R.id.txtQuantity);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        txtQunatity.setText(String.valueOf(quantity));

    }
    private void initButtons() {
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canOrderMore()){
                    sendOrderToCart();
                    Toast.makeText(PizzaDetails.this, "Вашата нарачка е успешно ставена во кошничка!", Toast.LENGTH_LONG).show();
                }


            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity < 10){
                    quantity++;
                }
                txtQunatity.setText(String.valueOf(quantity));
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1){
                    quantity --;
                }
                txtQunatity.setText(String.valueOf(quantity));
            }
        });

    }

    private void sendOrderToCart() {
        try{

                order = new Order();
                order.setProductID(id);
                order.setProductName(pizza.getName());
                order.setPrice(pizza.Price);
                order.setQuantity(String.valueOf(quantity));
                listOrder = new ArrayList<>();
                total = 0;
                orderRepository.insertItem(order);




        }catch (Exception e){
            Log.d("ORDER", e.getMessage());
        }
        //goToMyOrder();
    }

    public boolean canOrderMore() {
        final boolean[] pom = {true};
        orderRepository.listAllOrders().observe(PizzaDetails.this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orders) {
                if(orders != null && orders.size() > 0){
                    for(Order o : orders){
                        if(o.getProductID().equals(order.getProductID())){

                            pom[0] =false;
                          //  Toast.makeText(PizzaDetails.this,"Ја имате веќе нарачано истата пица!",
                             //       Toast.LENGTH_LONG).show();
//                            int tmp = Integer.parseInt(o.getQuantity()) + Integer.parseInt(order.getQuantity());
//                            order.setQuantity(String.valueOf(tmp));
//                            orderRepository.update(order);
                        }
                        //listOrder.add(o);
                    }
                }
            }
        });

        return pom[0];
    }


    private void goToMyOrder() {
        Intent i = new Intent(this, MyOrderForm.class);
        startActivity(i);
    }

    private void LoadPizza(){
        DatabaseReference pizzaRef = dbRef.child("Pizzas");

        pizzaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    for(DataSnapshot post : dataSnapshot.getChildren()){
                        HashMap<String, String> map = (HashMap<String, String>) post.getValue();
                        pizza = new Pizza(map.get("name"), map.get("ingredients"), map.get("price"), map.get("poster"), map.get("pizzaId"));
                        if(pizza.checkID(id)){
                            setData(pizza);
                            break;
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB_ERROR1", databaseError.getMessage());
            }
        });
    }

    private void setData(Pizza pizza) {
        Glide.with(this)
                .load(pizza.getImg())
                .placeholder(R.mipmap.ic_launcher)
                .into(imgView);

        pizzaName.setText(pizza.Name);
        pizzaPrice.setText(pizza.Price);
        pizzaIngredients.setText(String.format("Состојки:\n%s", pizza.Ingredients));

    }
}
