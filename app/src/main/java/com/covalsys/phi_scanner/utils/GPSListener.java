package com.covalsys.phi_scanner.utils;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by CBS on 12-08-2020.
 */
public class GPSListener implements LocationListener {

    private Activity activity;
    private LocationManager lm;
    private int numberOfUpdates;

    public static final int MAX_NUMBER_OF_UPDATES = 10;

    public GPSListener(Activity activity, LocationManager lm) {
        this.activity = activity;
        this.lm = lm;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (numberOfUpdates < MAX_NUMBER_OF_UPDATES) {
            numberOfUpdates++;

            Log.w("LAT", String.valueOf(location.getLatitude()));
            Log.w("LONG", String.valueOf(location.getLongitude()));
            Log.w("ACCURACY", String.valueOf(location.getAccuracy() + " m"));
            Log.w("PROVIDER", String.valueOf(location.getProvider()));
            Log.w("SPEED", String.valueOf(location.getSpeed() + " m/s"));
            Log.w("ALTITUDE", String.valueOf(location.getAltitude()));
            Log.w("BEARING", String.valueOf(location.getBearing() + " degrees east of true north"));

            String message;

            if (location != null) {
                message = "Current location is:  Latitude = "
                        + location.getLatitude() + ", Longitude = "
                        + location.getLongitude();
                // lm.removeUpdates(this);
            } else
                message = "Location null";

            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        } else {
            lm.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(activity, "Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(activity, "Gps Disabled", Toast.LENGTH_SHORT).show();
    }
}
