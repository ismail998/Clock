package com.ismailbelgacem.clock.Adapter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ismailbelgacem.clock.Data.AlarmRspo;
import com.ismailbelgacem.clock.Model.Alarme;

import java.util.List;

public class ViewModelRecyclerView extends AndroidViewModel {
    private AlarmRspo alarmRepository;
    private LiveData<List<Alarme>> alarmsLiveData;
    public ViewModelRecyclerView(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRspo(application);
        alarmsLiveData = alarmRepository.getAlarmsLiveData();
    }
    public void update(Alarme alarm) {
        alarmRepository.update(alarm);
    }

    public LiveData<List<Alarme>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
