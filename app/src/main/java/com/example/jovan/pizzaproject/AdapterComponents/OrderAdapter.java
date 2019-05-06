package com.example.jovan.pizzaproject.AdapterComponents;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jovan.pizzaproject.Models.Order;
import com.example.jovan.pizzaproject.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>  {
    List<Order> orders;
    Context context;

    public  OrderAdapter(Context context, List<Order> orders){
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_holder, viewGroup, false);
        final OrderViewHolder viewHolder = new OrderViewHolder(view);
        viewHolder.setParent(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int i) {
        final Order order = orders.get(i);
        viewHolder.bind(order);
    }

    public Order getItemAt(int pos){
        return orders.get(pos);
    }

    public void clearAll(){
        orders.clear();
    }

    @Override
    public int getItemCount() {
       return orders != null ? orders.size() : 0;
    }
    public void updateData(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }
}
