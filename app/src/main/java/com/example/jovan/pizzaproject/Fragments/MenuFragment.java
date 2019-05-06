package com.example.jovan.pizzaproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;
import com.example.jovan.pizzaproject.AdapterComponents.RecyclerViewAdapter;
import com.example.jovan.pizzaproject.Models.Pizza;
import com.example.jovan.pizzaproject.R;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Button btn;
    List<Pizza> pizzas;
    FirebaseDatabase db;
    DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.menu_fragment, container, false);
        initUI(view);
        LoadData(view);
      //  testingTheList();
        return view;
    }

    private void LoadData(View view) {
        final DatabaseReference pizzaRef = dbRef.child("Pizzas");
        pizzas = new ArrayList<Pizza>();
        pizzaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    for(DataSnapshot post : dataSnapshot.getChildren()){
                        HashMap<String, String> map = (HashMap<String, String>) post.getValue();
                        Pizza pizza = new Pizza(map.get("name"), map.get("ingredients"), map.get("price"), map.get("poster"), map.get("pizzaId"));
                        pizzas.add(pizza);
                        Log.d("PIZZA", pizza.Name);
                    }
                    adapter.updateData(pizzas);
                }catch (Exception e){
                    Log.d("DB_ERROR", e.getMessage());
                    Toast.makeText(getContext(), "TUKA", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB_ERROR", databaseError.getMessage());
            }
        });
    }

    private void initUI(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(new ArrayList<Pizza>(), getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();
    }

    private void testingTheList(){
        Pizza pizza = new Pizza("Pepperoni", "Pepperoni, cheese, tomato sauce" , "320", null, "0");
        pizzas.add(pizza);
        pizza = new Pizza("Cheesy", "Bacon, cheese, tomato sauce, mushrooms" , "300", null, "1");
        pizzas.add(pizza);
        pizza = new Pizza("Margarita", "Cheese, basil, tomato sauce" , "280", null, "2");
        pizzas.add(pizza);

    }
}
