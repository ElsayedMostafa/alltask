package com.example.madara.section3.sensors;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.madara.section3.MainActivity;
import com.example.madara.section3.Utils;

import java.io.File;

/**
 * Created by madara on 5/1/18.
 */

public class GpsService extends Service implements LocationListener {
    private static final String TAG = "Gps Service";
    private  LocationManager mLocationManager;
    final String FILE_NAME = "gps.csv";
    Utils writer = new Utils();
    String path = "/storage/emulated/0/Android/data/com.example.madara.section3/files/Documents/" ;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.e(TAG,"here");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return START_NOT_STICKY;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        File file = new File(path, FILE_NAME);

        if(!file.exists()){
            //rwfile.write(FILE_NAME, headers, this);
            writer.writeOnFile(FILE_NAME,path, "Latitude,Longitude");
            Log.e(TAG, path);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            String con = String.valueOf(latitude)+String.valueOf(longitude);
            Log.e("GeoLocation","Longitude"+longitude+" latitude"+latitude);
            writer.writeOnFile(FILE_NAME,path,con);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(this);
    }
}
