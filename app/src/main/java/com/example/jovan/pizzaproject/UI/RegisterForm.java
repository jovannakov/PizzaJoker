package com.example.jovan.pizzaproject.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jovan.pizzaproject.MainActivity;
import com.example.jovan.pizzaproject.Models.User;
import com.example.jovan.pizzaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterForm extends AppCompatActivity {

    Button btnRegister;

    TextView loginLink;
    ImageView imgLink;
    EditText fname;
    EditText lname;
    EditText email;
    EditText pasw1;
    EditText pasw2;

    FirebaseAuth mAuth;
    FirebaseDatabase fDb;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        initUI();
        initActions();
    }

    private void initUI(){
        loginLink = findViewById(R.id.link_login);
        imgLink = findViewById(R.id.logo_meni);
        fname = findViewById(R.id.firstMame);
        lname = findViewById(R.id.lastName);
        email = findViewById(R.id.input_email);
        pasw1 = findViewById(R.id.input_password);
        pasw2 = findViewById(R.id.input_password1);
        btnRegister = findViewById(R.id.btn_signup);

        mAuth = FirebaseAuth.getInstance();
        fDb = FirebaseDatabase.getInstance();
        dbRef = fDb.getReference();
    }

    private boolean validate(){
        String fn = String.valueOf(fname.getText());
        String ln = String.valueOf(lname.getText());
        String em = String.valueOf(email.getText());
        String p1 = String.valueOf(pasw1.getText());
        String p2 = String.valueOf(pasw2.getText());

        boolean flag = true;

        if(fn.equals("")){
            //Toast.makeText(this, R.string.fname_err, Toast.LENGTH_LONG).show();
            fname.setError("Внесете име!");
            flag =  false;
        }

        if(ln.equals("")){
            //Toast.makeText(this, R.string.lname_err, Toast.LENGTH_LONG).show();
            lname.setError("Внесете презиме!");
            flag =  false;
        }

        if(em.equals("")){
            //Toast.makeText(this, R.string.email_err, Toast.LENGTH_LONG).show();
            email.setError("Внесете е-адреса!");
            flag =  false;
        }

        if(p1.equals("")){
            // Toast.makeText(this, R.string.p1_err, Toast.LENGTH_LONG).show();
            pasw1.setError("Внесете лозинка!");
            flag =  false;
        }

        if(p2.equals("")){
            //Toast.makeText(this, R.string.p2_err, Toast.LENGTH_LONG).show();
            pasw2.setError("Повторете ја лозинка!");
            flag =  false;
        }
        if(!p1.equals(p2)){
            Toast.makeText(this, R.string.notmatch_err, Toast.LENGTH_LONG).show();
            pasw1.setError("Лозинките не се совпаѓаат!");
            pasw2.setError("Лозинките не се совпаѓаат!");
            flag =  false;
        }

        return flag;
    }

    private void initActions(){
        imgLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginForm();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    register();
                }
            }
        });
    }

    private void register() {
        final String em = String.valueOf(email.getText());
        final String p1 = String.valueOf(pasw1.getText());

        mAuth.createUserWithEmailAndPassword(em, p1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String fn = String.valueOf(fname.getText());
                            String ln = String.valueOf(lname.getText());
                            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(fn, ln, em);

                            try{
                                dbRef.child("users").child(uId).push().setValue(user);

                                Intent i = new Intent(RegisterForm.this, LoginForm.class);
                                i.putExtra("email", em);
                                i.putExtra("password", p1);
                                startActivity(i);
                            }catch (Exception ex){
                                Toast.makeText(RegisterForm.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        }else{
                            Toast.makeText(RegisterForm.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }


    private void goToMainActivity() {
        Intent i = new Intent(RegisterForm.this, MainActivity.class);
        startActivity(i);
    }

    private void goToLoginForm(){
        Intent i = new Intent(RegisterForm.this, LoginForm.class);
        startActivity(i);
    }

}
