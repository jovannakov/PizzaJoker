package com.example.jovan.pizzaproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jovan.pizzaproject.Models.User;
import com.example.jovan.pizzaproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;

public class ProfileFragment extends Fragment {

    TextView textView;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference dbRef;
    String mail;
    EditText name,lastname,eMail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container,  false);
        init(view);
        return view;
    }

    private void init(View view) {
       name=(EditText)view.findViewById(R.id.name);
       lastname=(EditText)view.findViewById(R.id.lastName);
       eMail = (EditText) view.findViewById(R.id.eMail);
       db = FirebaseDatabase.getInstance();
       dbRef = db.getReference().child("users");

        mAuth = FirebaseAuth.getInstance();
        mail = mAuth.getCurrentUser().getEmail();

        load();
        
       // loadData();
    }

    private void  load() {

        DatabaseReference userRef = dbRef.child(mAuth.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    Log.d("onDataChange", "Not empty");
                    try{
                        HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
                        Collection<String> collection = map.values();
                        HashMap<String, String> user_map = (HashMap<String, String>) map.values().toArray()[0];
                        String str = map.get("firstName");
                        Log.d("MODEL_OBJ", user_map.get("firstName"));
                        setData(new User(user_map.get("firstName"), user_map.get("lastName"), user_map.get("eMail")));
                    }catch (Exception ex){
                        Log.d("onDataChange", "Exception: " + ex.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelled", databaseError.getMessage());
            }
        });
    }


    private void setData(User user){
        try{
            name.setText(user.firstName);
            lastname.setText(user.lastName);
            eMail.setText(user.eMail);
            Log.d("MODEL_OBJ", "SUCCESS");
        }catch (Exception ex){
            Log.d("EXCEPTION", ex.getMessage());
        }
    }

    private void loadData(){
        String id = mAuth.getCurrentUser().getUid();
        Log.d("UID", id);
        DatabaseReference childRef = dbRef.child("users");

        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot post : dataSnapshot.getChildren()){
                        HashMap<String, String> map = (HashMap<String, String>) post.getValue();
                        Log.d("EMAIL", "JOVAN");
                        if(mail.equals(map.get("eMail"))){
                            Log.d("EQUALS","into EQ");
                            try{
                                User user = new User(map.get("firstName"), map.get("lastName"), map.get("eMail"));
                                setData(user);
                                Log.d("USER1", String.format("firstName: %s, lastName: %s, eMail: %s", user.firstName, user.lastName, user.eMail));
                            }catch (Exception ex){
                                Log.d("EXCEPTIONU", ex.getMessage());
                            }
                            return;
                        }

                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelled", "CANCELLED");
            }
        });

    }









}
