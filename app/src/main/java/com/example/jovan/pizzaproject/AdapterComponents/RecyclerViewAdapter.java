package com.example.jovan.pizzaproject.AdapterComponents;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jovan.pizzaproject.Models.Pizza;
import com.example.jovan.pizzaproject.R;
import com.example.jovan.pizzaproject.UI.PizzaDetails;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Pizza> pizzas;
    private Context context;

    public RecyclerViewAdapter(List<Pizza> pizzas, Context context){
        this.pizzas = pizzas;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_holder, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setParent(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            final Pizza pizza = pizzas.get(i);
            viewHolder.bind(pizza);
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToPizzaDetailsActivity(v, pizza);
                }
            });
    }

    private void goToPizzaDetailsActivity(View v,Pizza pizza){
        Intent i = new Intent(context, PizzaDetails.class);
        i.putExtra("id", pizza.PizzaID);
        context.startActivity(i);
    }


    @Override
    public int getItemCount() {
        return pizzas != null ? pizzas.size() : 0;
    }

    public void updateData(List<Pizza> pizzas){
        this.pizzas = pizzas;
        notifyDataSetChanged();
    }
}
