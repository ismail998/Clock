package com.ismailbelgacem.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ismailbelgacem.clock.Model.Alarme;

import java.util.Calendar;
import java.util.Random;

public class RaingActivity extends AppCompatActivity {

    Button dismiss;
    Button snooze;
    ImageView clock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raing);
        dismiss= findViewById(R.id.activity_ring_dismiss);
        snooze= findViewById(R.id.activity_ring_snooze);
        clock = findViewById(R.id.activity_ring_clock);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 10);
                Alarme alarm = new Alarme(
                        new Random().nextInt(Integer.MAX_VALUE),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,false
                        ,false,
                        "Snooze",
                        System.currentTimeMillis()
                );

                alarm.schedule(getApplicationContext());
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

    }
}