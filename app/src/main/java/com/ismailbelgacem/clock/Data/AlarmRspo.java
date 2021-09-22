package com.ismailbelgacem.clock.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ismailbelgacem.clock.Model.Alarme;

import java.util.List;

public class AlarmRspo {
    private  AlarmDoe alarmDoe;
    private LiveData<List<Alarme>> listLiveData;
  public  AlarmRspo(Application application){
     DataBaseAlarm db = DataBaseAlarm.getDatabase(application);
     alarmDoe = db.alarmDoe();
     listLiveData= alarmDoe.getAlarms();
  }
    public void insert(Alarme alarm) {
        DataBaseAlarm.databaseWriteExecutor.execute(() -> {
            alarmDoe.insert(alarm);
        });
    }
    public void delete(Alarme alarme){
      DataBaseAlarm.databaseWriteExecutor.execute(() ->{
          alarmDoe.delete(alarme);
      });
    }
    public void update(Alarme alarm) {
        DataBaseAlarm.databaseWriteExecutor.execute(() -> {
            alarmDoe.update(alarm);
        });
    }

    public LiveData<List<Alarme>> getAlarmsLiveData() {
        return listLiveData;
    }

}

