package com.example.jovan.pizzaproject.AdapterComponents;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jovan.pizzaproject.Models.Pizza;
import com.example.jovan.pizzaproject.R;
import com.example.jovan.pizzaproject.UI.PizzaDetails;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imgView;
    TextView txtView;
    View parent;
    String pizzaID;
    LinearLayout linearLayout;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        initUI(itemView);


    }

    public void bind(final Pizza pizza){
        pizzaID = pizza.PizzaID;
        Glide.with(itemView.getContext())
                .load(pizza.getImg())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(getImgView());
        getTxtView().setText(pizza.getName());
    }

    private void initUI(View itemView){
        this.imgView = (ImageView) itemView.findViewById(R.id.imgView);
        this.txtView = (TextView) itemView.findViewById(R.id.dataPizza);
        this.linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_lay);
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    public void setTxtView(TextView txtView) {
        this.txtView = txtView;
    }

    public TextView getTxtView() {
        return txtView;
    }

    public View getParent(){
        return parent;
    }
    public void setParent(View parent){
        this.parent = parent;
    }
}
