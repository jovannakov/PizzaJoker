package com.example.jovan.pizzaproject.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jovan.pizzaproject.MainActivity;
import com.example.jovan.pizzaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginForm extends AppCompatActivity {

    TextView registerLink;
    ImageView imgLink;
    EditText email;
    EditText passw;
    Button btnLogin;

    ActivityHeader ah;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        initUI();
        initActions();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        return false;
    }

    private void initUI(){
        registerLink = findViewById(R.id.link_signup);
        imgLink = findViewById(R.id.logo_meni);
        email = findViewById(R.id.input_email);
        passw = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        email.setText("");
        passw.setText("");
        mAuth = FirebaseAuth.getInstance();
    }

    private void initActions(){
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterForm();
            }
        });
        imgLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String em = email.getText().toString();
        String p1 = passw.getText().toString();

        // need to delete this only for testing
        //goToMenu();

        if(validate(em, p1)){
            mAuth.signInWithEmailAndPassword(em, p1)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                goToMenu();
                            }else{
                                Toast.makeText(LoginForm.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private boolean validate(String em, String pw){
        boolean flag = true;
        if(em.equals("")){
           // Toast.makeText(LoginForm.this, R.string.email_err, Toast.LENGTH_LONG).show();
            email.setError("Внесете е-адреса!");
            flag =  false;
        }
        if(pw.equals("")){
            // Toast.makeText(LoginForm.this, R.string.p1_err, Toast.LENGTH_LONG).show();
            passw.setError("Внесете лозинка!");
            flag = false;
        }
        return flag;
    }

    private void goToMenu() {
        Intent i = new Intent(this, SideMenu.class);
        startActivity(i);
    }

    private void goToMainActivity() {
        Intent i = new Intent(LoginForm.this, MainActivity.class);
        startActivity(i);
    }

    private void goToRegisterForm(){
        Intent i = new Intent(LoginForm.this, RegisterForm.class);
        startActivity(i);
    }


}
