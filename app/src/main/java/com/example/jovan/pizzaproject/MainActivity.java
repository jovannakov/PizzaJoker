package com.example.jovan.pizzaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jovan.pizzaproject.Persistence.Repository.OrderRepository;
import com.example.jovan.pizzaproject.UI.LoginForm;
import com.example.jovan.pizzaproject.UI.RegisterForm;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;
    OrderRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repo = new OrderRepository(getApplicationContext());
        repo.deleteAll();
        initUI();
    }

    private void initUI(){
        btnLogin = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginForm();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterForm();
            }
        });
    }

    private void goToLoginForm(){
        Intent i = new Intent(this, LoginForm.class);
        startActivity(i);
    }

    private void goToRegisterForm(){
        Intent i = new Intent(this, RegisterForm.class);
        startActivity(i);
    }
}
