package com.ismailbelgacem.clock;

import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.RINGMATH;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.RINGOFF;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.TITLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ismailbelgacem.clock.Model.Alarme;

import java.util.Calendar;
import java.util.Random;

public class RaingActivity extends AppCompatActivity {
    int a,b ,r;
    TextView textnumber;
    EditText editRusalt;
    Button dismiss;
    Button snooze;
    ImageView clock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raing);
        getSupportActionBar().hide();

        dismiss= findViewById(R.id.activity_ring_dismiss);
        snooze= findViewById(R.id.activity_ring_snooze);
        textnumber = findViewById(R.id.textnumber);
        editRusalt = findViewById(R.id.number);
        LinearLayout line1 = findViewById(R.id.line1);
        boolean test =getIntent().getBooleanExtra(RINGMATH,false);
        boolean test1 =getIntent().getBooleanExtra(RINGOFF,false);

        Log.d("TAG", "onCreate: " +test1);
        if (test){
            dismiss.setVisibility(View.GONE);
            snooze.setVisibility(View.GONE);
            Random random = new Random();
            a = random.nextInt(20);
            b=random.nextInt(10);
            textnumber.setText(a+"+" +b+"=");
            r=a+b;
            editRusalt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        // Do whatever you want here
                  int  re= Integer.parseInt(editRusalt.getText().toString());
                    if (r==re){
                        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                        getApplicationContext().stopService(intentService);
                        finish();
                    }else {
                    }
                        return true;
                    }
                    return false;
                }
            });
        }else {

        line1.setVisibility(View.GONE);
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
}