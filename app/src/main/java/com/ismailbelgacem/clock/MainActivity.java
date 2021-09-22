package com.ismailbelgacem.clock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismailbelgacem.clock.Adapter.AdapterRecyclerView;
import com.ismailbelgacem.clock.Adapter.OnToggleAlarmListener;
import com.ismailbelgacem.clock.Adapter.ViewModelRecyclerView;
import com.ismailbelgacem.clock.Model.Alarme;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnToggleAlarmListener {
    private AdapterRecyclerView alarmRecyclerViewAdapter;
    private ViewModelRecyclerView alarmsListViewModel;
    private RecyclerView alarmsRecyclerView;
    private FloatingActionButton addAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmsRecyclerView = findViewById(R.id.recycler_alarm);
        addAlarm = findViewById(R.id.floatingActionButton2);
       alarmRecyclerViewAdapter = new AdapterRecyclerView(this,getApplicationContext());
       alarmsListViewModel = ViewModelProviders.of(this).get(ViewModelRecyclerView.class);
       alarmsListViewModel.getAlarmsLiveData().observe(this, new Observer<List<Alarme>>() {
           @Override
           public void onChanged(List<Alarme> alarmes) {
               if (alarmes != null) {
                   alarmRecyclerViewAdapter.setAlarms(alarmes);
               }
           }
       });
       addAlarm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MainActivity.this,AddNewAlarme.class);
               startActivity(i);
           }
       });
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager((this)));
        alarmsRecyclerView.setAdapter(alarmRecyclerViewAdapter);
    }
    @Override
    public void onToggle(Alarme alarm) {
        if (alarm.isStarted()) {
            alarm.cancelAlarm(this);
            alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(this);
            alarmsListViewModel.update(alarm);
        }
    }
}