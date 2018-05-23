package com.example.madara.section3;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.madara.section3.sensors.CompassService;
import com.example.madara.section3.sensors.GpsService;
import com.example.madara.section3.sensors.GyroService;
import com.example.madara.section3.sensors.MagneticService;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    // gps, gyro, compass, orientation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File mypath = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        Log.e("path",mypath.toString());
        final Intent gps_intent = new Intent(getApplicationContext(),GpsService.class);
        final Intent gyro_intent = new Intent(getApplicationContext(),GyroService.class);
        final Intent mag_intent = new Intent(getApplicationContext(),MagneticService.class);
        final Intent com_intent = new Intent(getApplicationContext(),CompassService.class);
        final Button _btn_startmag = (Button) findViewById(R.id.btn_startmag);
        final Button _btn_stopmag = (Button) findViewById(R.id.btn_stopmag);
        final Button _btn_startgps = (Button) findViewById(R.id.btn_startgps);
        final Button _btn_stopgps = (Button) findViewById(R.id.btn_stopgps);
        final Button _btn_startgyro = (Button) findViewById(R.id.btn_startgyro);
        final Button _btn_stopgyro = (Button) findViewById(R.id.btn_stopgyro);
        final Button _btn_startcom = (Button) findViewById(R.id.btn_startcom);
        final Button _btn_stopcom = (Button) findViewById(R.id.btn_stopcom);
        _btn_startgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("here","here");
                startService(gps_intent);
                _btn_stopgps.setEnabled(true);
                _btn_startgps.setEnabled(false);

            }
        });
        _btn_stopgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(gps_intent);
                _btn_stopgps.setEnabled(false);
                _btn_startgps.setEnabled(true);
            }
        });
        _btn_startgyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(gyro_intent);
                _btn_stopgyro.setEnabled(true);
                _btn_startgyro.setEnabled(false);
            }
        });
        _btn_stopgyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(gyro_intent);
                _btn_stopgyro.setEnabled(false);
                _btn_startgyro.setEnabled(true);
            }
        });
        _btn_startmag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(mag_intent);
                _btn_stopmag.setEnabled(true);
                _btn_startmag.setEnabled(false);
            }
        });
        _btn_stopmag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(mag_intent);
                _btn_stopmag.setEnabled(false);
                _btn_startmag.setEnabled(true);
            }
        });
        _btn_startcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(com_intent);
                _btn_stopcom.setEnabled(true);
                _btn_startcom.setEnabled(false);
            }
        });
        _btn_stopcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(com_intent);
                _btn_stopcom.setEnabled(false);
                _btn_startcom.setEnabled(true);
            }
        });
    }


}
