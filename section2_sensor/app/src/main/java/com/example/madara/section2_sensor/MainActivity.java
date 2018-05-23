package com.example.madara.section2_sensor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private AlarmManager mScheduler;
    private PendingIntent mScheduledIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button _Start = (Button) findViewById(R.id.btn_start);
        Button _Stop = (Button) findViewById(R.id.btn_stop);
        final TextView _stat = (TextView) findViewById(R.id.tv_stat);
        //File mypath = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        //Log.e("here",mypath.getAbsolutePath());
        //storage/emulated/0/Android/data/com.example.madara.section2_sensor/files/Documents
        _Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScheduler = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AccLoggerService.class);
                mScheduledIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                _stat.setText("Service Running");
                mScheduler.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000, mScheduledIntent);

            }
        });
        _Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScheduler.cancel(mScheduledIntent);
                _stat.setText("Service Stopped");
            }
        });


    }
}
