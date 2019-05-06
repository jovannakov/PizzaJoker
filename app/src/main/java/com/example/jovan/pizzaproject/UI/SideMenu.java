package com.example.jovan.pizzaproject.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jovan.pizzaproject.Fragments.MenuFragment;
import com.example.jovan.pizzaproject.Fragments.ProfileFragment;
import com.example.jovan.pizzaproject.MainActivity;
import com.example.jovan.pizzaproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class SideMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fDb;
    private DatabaseReference dbRef;
    private static int REQUEST_CODE = 1;

    CarouselView carouselView;

    private int[]  imagespizzas=new int[]{
            R.drawable.pica4,R.drawable.pica1,R.drawable.heart_pizza,R.drawable.pica3, R.drawable.delivery
    };

    private String[] pizzasTitles=new String[]{
            "Pizza1","Pizza2","Pizza3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
        drawer = findViewById(R.id.drawer_layout);
        carouselView=findViewById(R.id.carousel);
        carouselView.setPageCount(imagespizzas.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                    imageView.setImageResource(imagespizzas[position]);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        init();
        // setData();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return false;
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        fDb = FirebaseDatabase.getInstance();
        dbRef = fDb.getReference();
    }

    private void setData(){
        String uId = mAuth.getCurrentUser().getUid();
        String email = mAuth.getCurrentUser().getEmail();

    }

    private void SignOut(){
        Toast.makeText(this, "Одјава...", Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MenuFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_order:
                goToMyOrder();
                break;
            case R.id.nav_exit:
                SignOut();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);


        return false;
    }

    private void goToMyOrder() {
        Intent intent = new Intent(this, MyOrderForm.class);
        startActivity(intent);
    }
}
