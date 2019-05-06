package com.example.jovan.pizzaproject.UI;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.jovan.pizzaproject.AdapterComponents.RecyclerViewAdapter;
import com.example.jovan.pizzaproject.Models.Pizza;
import com.example.jovan.pizzaproject.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    Button btn;
    List<Pizza> pizzas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.menu_fragment);
        initUI();

      //  ActionBar actionBar=getSupportActionBar();

    }

    private void initUI(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(new ArrayList<Pizza>(), this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Syncing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                adapter.updateData(pizzas);
            }
        });
    }


}
