package com.example.silentnotification.loc_utility;

import android.location.Location;
import android.location.LocationListener;

public interface MyLocationListener extends LocationListener {
    public void onLocationChanged(Location location);
}
