package com.example.madara.accelerometer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AlarmManager mScheduler;
    private PendingIntent mScheduledIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button _btn_start = (Button) findViewById(R.id.btn_start);
        final Button _btn_stop = (Button) findViewById(R.id.btn_stop);
        _btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScheduler = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), Logservice.class);
                _btn_stop.setVisibility(View.VISIBLE);
                _btn_start.setVisibility(View.GONE);
                mScheduledIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mScheduler.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, mScheduledIntent);
            }
        });
        _btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScheduler.cancel(mScheduledIntent);
                _btn_start.setVisibility(View.VISIBLE);
                _btn_stop.setVisibility(View.GONE);
            }
        });
    }
}
