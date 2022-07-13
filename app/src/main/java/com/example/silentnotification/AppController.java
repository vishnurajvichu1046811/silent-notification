package com.example.silentnotification;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.silentnotification.firebase.FirebaseRealtimeDB;
import com.example.silentnotification.loc_utility.GPSTracker;
import com.example.silentnotification.service.ForegroundService;
import com.example.silentnotification.service.LocationUpdatesService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    public static final String FETCH_USER_LOCATION = "fetch_location";
    private static AppController mInstance;
    private SharedPreferences sharedPref;
    private LocationUpdatesService mService = null;
    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            //mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            //mBound = false;
        }
    };

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
        //ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
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
                double altitude = 0.0;
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                altitude = gps.getAltitude();
                FirebaseRealtimeDB realtimeDB = new FirebaseRealtimeDB();
                String Username = Settings.Global.getString(getContentResolver(), Settings.Global.DEVICE_NAME);
                realtimeDB.addLocation(Username,latitude+"",longitude+"",altitude+"",new Timestamp(new Date().getTime())+"");

            }
        }
    };
/*
    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {

        if( event == Lifecycle.Event.ON_STOP){
            new Handler().postDelayed(()-> {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    //mService.requestLocationUpdates();
                    this.startForegroundService(new Intent(this,ForegroundService.class));
                }else{
                    this.startService(new Intent(this, ForegroundService.class));
                }
            },5000);
        }

    }*/
}
