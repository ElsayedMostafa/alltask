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
import java.sql.Timestamp;
import java.util.EventListener;

/**
 * Created by madara on 5/1/18.
 */

public class GyroService extends Service implements SensorEventListener {
    private static final String TAG = "Gyroservice";
    private SensorManager mSensorManager;
    private Sensor mGyro;
    private  double lastTimeStamp;
    private float currentAngleX ;
    private float currentAngleY;
    final String FILE_NAME = "gyro.csv";
    Utils writer = new Utils();
    String path = "/storage/emulated/0/Android/data/com.example.madara.section3/files/Documents/" ;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Log.e(TAG,"start");
        if(mGyro == null){
            Log.e(TAG,"no gyro");
            stopSelf();
        }
        mSensorManager.registerListener(this,mGyro,SensorManager.SENSOR_DELAY_FASTEST);
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
        Log.e(TAG,sensorEvent.values.toString());
        double dt = (sensorEvent.timestamp - lastTimeStamp);
        float axisX = sensorEvent.values[0] ;
        float axisY = sensorEvent.values[1];
        float axisZ = sensorEvent.values[2];
        currentAngleX = (float) (currentAngleX* axisX * dt*180/Math.PI)%360;
        currentAngleY = (float) (currentAngleY* axisY * dt*180/Math.PI)%360;
        String con = axisX+","+axisY+","+axisZ+","+currentAngleX+","+currentAngleY;
        lastTimeStamp = sensorEvent.timestamp;
        writer.writeOnFile(FILE_NAME,path,con);

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
