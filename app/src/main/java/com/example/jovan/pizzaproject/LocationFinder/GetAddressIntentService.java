package com.example.jovan.pizzaproject.LocationFinder;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class GetAddressIntentService extends IntentService {

    private static final String IDENTIFIER = "GetAddressIntentService";
    private ResultReceiver addressResultReceiver;

    public GetAddressIntentService() {
        super(IDENTIFIER);
    }

    //handle the address request
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg = "";
        //get result receiver from intent
        addressResultReceiver = intent.getParcelableExtra("add_receiver");

        if (addressResultReceiver == null) {
            Log.e("GetAddressIntentService",
                    "No receiver, not processing the request further");
            return;
        }

        Location location = intent.getParcelableExtra("add_location");

        //send no location error to results receiver
        if (location == null) {
            msg = "No location, can't go further without location";
            sendResultsToReceiver(0, msg);
            return;
        }
        //call GeoCoder getFromLocation to get address
        //returns list of addresses, take first one and send info to result receiver
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        if (addresses == null || addresses.size()  == 0) {
            msg = "No address found for the location";
            sendResultsToReceiver(1, msg);
        } else {
            Address address = addresses.get(0);
            StringBuffer addressDetails = new StringBuffer();

            addressDetails.append(address.getAddressLine(0));


            sendResultsToReceiver(2,addressDetails.toString());
        }
    }
    //to send results to receiver in the source activity
    private void sendResultsToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("address_result", message);
        addressResultReceiver.send(resultCode, bundle);
    }
}