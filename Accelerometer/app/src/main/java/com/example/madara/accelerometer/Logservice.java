package com.example.madara.accelerometer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by madara on 2/26/18.
 */

public class Logservice extends Service implements SensorEventListener {
    private static final String DEBUG_TAG = "accLoggerservice";
    private SensorManager sensorManger = null;
    private Sensor sensor = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManger = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManger.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        new SensorEventLoggerTask().execute(event);
        sensorManger.unregisterListener(this);
        stopSelf();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private class SensorEventLoggerTask extends AsyncTask<SensorEvent,Void,Void>{

        @Override
        protected Void doInBackground(SensorEvent... events) {
            SensorEvent event = events[0];
            float X = event.values[0];
            float Y = event.values[1];
            float Z = event.values[2];
            //log
            String readings = X+"-"+Y+"-"+Z;
            writeOnFile("accelerometer.csv", readings);
            return null;

        }

    }
    public void writeOnFile(String filename, String content){
        try {
            File sdfile = android.os.Environment.getExternalStorageDirectory();
            FileWriter fstream = new FileWriter(sdfile.getAbsolutePath()+File.separator+filename,true);
            Log.e(DEBUG_TAG,sdfile.getAbsolutePath()+File.separator+filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
