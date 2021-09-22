package com.ismailbelgacem.clock.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ismailbelgacem.clock.Model.Alarme;

import java.util.List;

@Dao
public interface AlarmDoe {
    @Insert
    void insert(Alarme alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();
    @Delete
    void delete(Alarme alarme);
    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    LiveData<List<Alarme>> getAlarms();

    @Update
    void update(Alarme alarm);
}
