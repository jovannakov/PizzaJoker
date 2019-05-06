package com.example.jovan.pizzaproject.UI;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jovan.pizzaproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityHeader extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseDatabase fDb;
    private DatabaseReference dbRef;

    public TextView userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
    }
}
