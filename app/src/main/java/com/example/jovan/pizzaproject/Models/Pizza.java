package com.example.jovan.pizzaproject.Models;

import android.media.Image;
import android.support.annotation.NonNull;

public class Pizza {
    public String Price;
    public String Name;
    public String Ingredients;
    public String Img;
    public String PizzaID;

    public Pizza(String name, String ingredients, String price, String  img, String pizzaID) {
        this.Price = price;
        this.Name = name;
        this.Ingredients = ingredients;
        this.Img = img;
        this.PizzaID = pizzaID;
    }


    public String getImg(){
        return Img;
    }

    public  String getName(){return Name;}

    public String getIngredients() {
        return Ingredients;
    }

    public boolean checkID(String id){
        return this.PizzaID.equals(id);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Name: %s \nPrice: %i \nIngredients: %s");
    }
}
