package com.example.jovan.pizzaproject.UI;

import android.Manifest;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.Handler;
import android.os.PatternMatcher;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.craftman.cardform.CardForm;
import com.example.jovan.pizzaproject.LocationFinder.GetAddressIntentService;
import com.example.jovan.pizzaproject.Models.Order;
import com.example.jovan.pizzaproject.Persistence.Repository.OrderRepository;
import com.example.jovan.pizzaproject.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public class PayActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    private LocationAddressResultReceiver addressResultReceiver;

    private TextView currentAddTv;

    private Location currentLocation;

    private LocationCallback locationCallback;

    private RadioGroup radioPayGroup;
    private RadioButton radioPayWay;
    private Button btnFinish;
    private OrderRepository orderRepository;
    private EditText telNum;
    private String Address = "";
    public  int total;
    public  String cost;
    CardForm cardForm;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        cardForm = (CardForm) findViewById(R.id.card_form);
        radioPayGroup=findViewById(R.id.radioPay);
        btnFinish=findViewById(R.id.btnFinish);
        linearLayout=findViewById(R.id.info);
        telNum = findViewById(R.id.telNum);
        orderRepository = new OrderRepository(getApplicationContext());
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();


        if (extras!=null){
            cost=extras.getString("totalPay");
        }


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("^07[01256789] [0-9]{3} [0-9]{3}$");
                Matcher matcher = pattern.matcher(telNum.getText());
                Pattern pattern1 = Pattern.compile("^07[01256789][0-9]{3}[0-9]{3}$");
                Matcher matcher1 = pattern.matcher(telNum.getText());
                if(!matcher.find()){
                    Toast.makeText(PayActivity.this, "Задолжително внесете телефонски број!", Toast.LENGTH_LONG).show();
                }else{
                    if(currentAddTv.getText().equals("")){
                        Toast.makeText(getApplicationContext(), "Вклучете ја локацијата, или почекајте неколку секунди апликацијата да ве лоцира.", Toast.LENGTH_SHORT).show();
                    }else {
                        int selectedPay = radioPayGroup.getCheckedRadioButtonId();
                        radioPayWay = (RadioButton) findViewById(selectedPay);
                        if (selectedPay == R.id.radioCard) {
                            Toast.makeText(PayActivity.this,
                                    radioPayWay.getText(), Toast.LENGTH_SHORT).show();
                            cardForm.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.GONE);
                            fillCard();
                        } else {
                            payOnHand();
                        }
                    }
                }
            }
        });

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());

        currentAddTv = findViewById(R.id.current_address);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            };
        };
        startLocationUpdates();
    }

    private void payOnHand() {
        ReadOrders();
        Toast.makeText(PayActivity.this, "Вашата нарачка е успешно испратена!", Toast.LENGTH_LONG).show();
    }


    private void ReadOrders(){
        try{
            LiveData<List<Order>> liveData = orderRepository.listAllOrders();
            liveData.observe(PayActivity.this, new Observer<List<Order>>() {
                @Override
                public void onChanged(@Nullable List<Order> orders) {
                    SendOrder(orders);
                }
            });
        }catch (Exception ex){
            Log.d("ORDER1", "EXCEPTION: " + ex.getMessage());
        }
    }

    private void SendOrder(List<Order> orders) {
        try{
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = db.getReference().child("Orders");
            // Hashmap<String, List<Order>> order = new Hashmap<String, List<Order>>();
            String telephone = String.format("+389 %s", telNum.getText().subSequence(1, telNum.getText().length()));
            HashMap<String, Object> order = new HashMap<String, Object>();
            order.put("Orders", orders);
            order.put("Tel-number", telephone);
            order.put("Address", Address);
            dbRef.push().setValue(order);
            orderRepository.deleteAll();
            GoToMenu();
        }catch (Exception ex){
            Log.d("ORDER", ex.getMessage());
        }
    }

    private void GoToMenu() {
        startActivity(new Intent(PayActivity.this, SideMenu.class));
    }


    public void  fillCard(){
        TextView price=(TextView)findViewById(R.id.payment_amount);
        Button btnPay=(Button)findViewById(R.id.btn_pay);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(cost);
        stringBuilder.append(" денари");
        price.setText(stringBuilder);
        btnPay.setText("Плати");
        btnPay.setBackgroundColor(getResources().getColor(R.color.colorBtnMain));

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ReadOrders();
                    Toast.makeText(getApplicationContext(), "Наплатата е успешно направена, нарачката е на пат!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(PayActivity.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(PayActivity.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");
            Address = currentAdd;
            showResults(currentAdd);
        }
    }

    private void showResults(String currentAdd){
        currentAddTv.setText(currentAdd);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}




