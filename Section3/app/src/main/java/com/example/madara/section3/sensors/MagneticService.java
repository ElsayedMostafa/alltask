package com.example.madara.section3.sensors;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.madara.section3.Utils;

import java.io.File;

/**
 * Created by madara on 5/1/18.
 */

public class MagneticService extends Service implements SensorEventListener {
    private static final String TAG = "Mag service";
    private SensorManager mSensorManager;
    private Sensor mMagnetic;
    final String FILE_NAME = "Magnato.csv";
    Utils writer = new Utils();
    String path = "/storage/emulated/0/Android/data/com.example.madara.section3/files/Documents/" ;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Log.e(TAG,"start");
        if(mMagnetic == null){
            Log.e(TAG,"no Magnetic field");
            stopSelf();
        }
        mSensorManager.registerListener(this,mMagnetic,SensorManager.SENSOR_DELAY_FASTEST);
        File file = new File(path, FILE_NAME);

        if(!file.exists()){
            //rwfile.write(FILE_NAME, headers, this);
            writer.writeOnFile(FILE_NAME,path, "");
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
    public void onSensorChanged(SensorEvent sensorEvent) {
        double megx = sensorEvent.values[0];
        double megy = sensorEvent.values[1];
        double megz = sensorEvent.values[2];
        Log.e(TAG,sensorEvent.values.toString());
        writer.writeOnFile(FILE_NAME,path,sensorEvent.values.toString());


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }
}
