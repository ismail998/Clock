package com.ismailbelgacem.clock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ismailbelgacem.clock.Model.Alarme;
import com.ismailbelgacem.clock.ViewModel.CreateAlarmViewModel;

import java.util.Random;

public class AddNewAlarme extends AppCompatActivity implements View.OnClickListener {

    private TimePicker timePicker;
    private CardView ringOff,ringMath;
    private TextInputLayout title;
    private Button scheduleAlarm,btnMon,btnTue,btnWed,btnThu,btnFri,btnSat,btnSun;
    private boolean bmon=false,btue=false,bwed=false,bthu=false,bfri=false,bsat=false,bsun=false,brecurring=true;
   boolean ringM= false,ringoff=false;
    private CreateAlarmViewModel createAlarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_alarme);
        defId();
        getSupportActionBar().hide();
        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);
        btnMon.setOnClickListener(this);
        btnFri.setOnClickListener(this);
        btnTue.setOnClickListener(this);
        btnSat.setOnClickListener(this);
        btnWed.setOnClickListener(this);
        btnThu.setOnClickListener(this);
        btnSun.setOnClickListener(this);
        ringOff.setOnClickListener(this);
        ringMath.setOnClickListener(this);
        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();
            }
        });
    }
    @SuppressLint("WrongViewCast")
    private void defId(){
        timePicker = findViewById(R.id.fragment_createalarm_timePicker);
        title = findViewById(R.id.fragment_createalarm_title);
        scheduleAlarm = findViewById(R.id.fragment_createalarm_scheduleAlarm);
        ringOff = findViewById(R.id.ringoff);
        ringMath = findViewById(R.id.ringMath);
        btnMon = findViewById(R.id.btnMon);
        btnWed = findViewById(R.id.btnWed);
        btnThu = findViewById(R.id.btnThu);
        btnFri = findViewById(R.id.btnFri);
        btnSat = findViewById(R.id.btnSat);
        btnSun = findViewById(R.id.btnSun);
        btnTue = findViewById(R.id.btnTue);
    }
    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        if(bmon==false && bfri==false && bsat == false && bsun == false && bwed ==false && btue ==false && bthu==false){
            brecurring =false;
        }
        Alarme alarm = new Alarme(
                alarmId,
                TimePickerUtil.getTimePickerHour(timePicker),
                TimePickerUtil.getTimePickerMinute(timePicker),
                true,
                brecurring,
                bmon,
                btue,
                bwed,
                bthu,
                bfri,
                bsat,
                bsun,
                ringoff,
                ringM
                ,title.getEditText().getText().toString(),
                System.currentTimeMillis()
        );
        createAlarmViewModel.insert(alarm);
        alarm.schedule(this);
        finish();
        Log.d("TAG", "scheduleAlarm: "+ringM);
    }

    public boolean selectDay(Button btn , boolean select){
        if (select){
            btn.setBackground(getDrawable(R.drawable.btn_not_select));
            select=false;
        }else {
            btn.setBackground(getDrawable(R.drawable.btnselectday));
            select=true;
        }
        Log.d("TAG", "selectDay: "+select);
     return select;
    }
    public boolean[] selectTypeRing(CardView cardView1,CardView cardView2,boolean select,boolean select2){
        if (select==false){
            if (select2== true){
                cardView1.setCardBackgroundColor(getColor(R.color.select));
                cardView2.setCardBackgroundColor(getColor(R.color.white));
                select=true;
                select2 =false;
            }else {
                cardView1.setCardBackgroundColor(getColor(R.color.select));
                select=true;
            }

        }else {
            cardView1.setCardBackgroundColor(getColor(R.color.white));
            select=false;
        }
        return  new boolean[]{select,select2};
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMon:
                bmon=selectDay(btnMon,bmon);
                Log.d("TAG", "onClick: "+bmon);
                break;
            case R.id.btnThu:
                bthu=selectDay(btnThu,bthu);
                break;
            case R.id.btnTue:
                btue=selectDay(btnTue,btue);
                break;
            case R.id.btnWed:
                bwed=selectDay(btnWed,bwed);
                break;
            case R.id.btnSun:
                bsun=selectDay(btnSun,bsun);
                break;
            case R.id.btnSat:
                bsat=selectDay(btnSat,bsat);
                break;
            case R.id.btnFri:
                bfri=selectDay(btnFri,bfri);
                break;
            case R.id.ringoff:
                boolean rusalt [] =selectTypeRing(ringOff,ringMath,ringoff,ringM);
                ringoff = rusalt[0];
                ringM = rusalt[1];
                break;
            case R.id.ringMath:
                boolean rusalt1 [] =selectTypeRing(ringMath,ringOff,ringM,ringoff);
                ringM = rusalt1[0];
                ringoff = rusalt1[1];
                break;
        }
    }
}