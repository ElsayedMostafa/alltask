package com.example.madara.section2_sensor;

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
 * Created by madara on 2/25/18.
 */

public class AccLoggerService extends Service implements SensorEventListener {
    private static final String DEBUG_TAG = "AccLoggerService";
    private Sensor mSensor = null;
    private SensorManager mSensorManager = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // return super.onStartCommand(intent, flags, startId);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        new SensorEventLoggerTask().execute(sensorEvent);
        mSensorManager.unregisterListener(this);
        stopSelf();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private class SensorEventLoggerTask extends
            AsyncTask<SensorEvent, Void, Void> {
        @Override
        protected Void doInBackground(SensorEvent... events) {
            SensorEvent event = events[0];
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];
            // log the value
//            Log.e(DEBUG_TAG,"show me "+ axisX);
//            Log.e(DEBUG_TAG,"show me "+ axisY);
//            Log.e(DEBUG_TAG,"show me "+ axisZ);
            String log = axisX+","+axisY+","+axisZ;
            writeOnFile("acc.csv", log);
            return null;
        }
    }
    public void writeOnFile(String filename, String content){
        try {
            File sdfile = android.os.Environment.getExternalStorageDirectory();
            Log.e(DEBUG_TAG,sdfile.getAbsolutePath());
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
