package com.example.jovan.pizzaproject.Models;

public class User {
    public String firstName;
    public String lastName;
    public String eMail;
    public String telNumber;

    public User(String firstName, String lastName, String eMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        //this.telNumber = telNumber;
    }

    public void setTelNumber(String telNumber){
        this.telNumber = telNumber;
    }
}
