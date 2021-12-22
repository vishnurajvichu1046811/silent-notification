package com.example.silentnotification;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


import com.example.silentnotification.firebase.FirebaseRealtimeDB;
import com.example.silentnotification.loc_utility.GPSTracker;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    public static final String FETCH_USER_LOCATION = "fetch_location";
    private static AppController mInstance;
    private SharedPreferences sharedPref;

    public static Boolean isConnected(final Activity activity) {
        Boolean check = false;
        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            check = true;
        } else {
            Toast.makeText(activity, activity.getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
        }
        return check;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(myReceiver, new IntentFilter(FETCH_USER_LOCATION));
        mInstance = this;
        sharedPref = this.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

    }

    public String getDeviceToken() {
        return sharedPref.getString("DEVICETOKEN", "");
    }

    public void setDeviceToken(String token) {
        sharedPref.edit().putString("DEVICETOKEN", token).apply();
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equalsIgnoreCase(FETCH_USER_LOCATION)){
                GPSTracker gps = new GPSTracker(context);
                double latitude = 0.0;
                double longitude = 0.0;
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                FirebaseRealtimeDB realtimeDB = new FirebaseRealtimeDB();
                realtimeDB.addLocation("user1",latitude+"",longitude+"",new Timestamp(new Date().getTime())+"");

            }
        }
    };

}
