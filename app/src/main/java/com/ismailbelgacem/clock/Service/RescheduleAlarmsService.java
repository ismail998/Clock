package com.ismailbelgacem.clock.Service;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.ismailbelgacem.clock.Data.AlarmRspo;
import com.ismailbelgacem.clock.Model.Alarme;

import java.util.List;

public class RescheduleAlarmsService extends LifecycleService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        AlarmRspo alarmRepository = new AlarmRspo(getApplication());
        alarmRepository.getAlarmsLiveData().observe(this, new Observer<List<Alarme>>() {
            @Override
            public void onChanged(List<Alarme> alarmes) {
                for (Alarme a : alarmes) {
                    if (a.isStarted()) {
                        a.schedule(getApplicationContext());
                    }
                }
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
