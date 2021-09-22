package com.ismailbelgacem.clock.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ismailbelgacem.clock.Data.AlarmRspo;
import com.ismailbelgacem.clock.Model.Alarme;

public class CreateAlarmViewModel extends AndroidViewModel {
    AlarmRspo alarmRspo;
    public CreateAlarmViewModel(@NonNull Application application) {
        super(application);
         alarmRspo = new AlarmRspo(application);
    }
    public void insert(Alarme alarm) {
        alarmRspo.insert(alarm);
    }
    public void delete (Alarme alarme){
        alarmRspo.delete(alarme);
    }
}
