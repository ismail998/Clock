package com.ismailbelgacem.clock.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ismailbelgacem.clock.Model.Alarme;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Alarme.class}, version = 1, exportSchema = false)
public abstract class DataBaseAlarm extends RoomDatabase {
public abstract AlarmDoe alarmDoe();
    private static volatile DataBaseAlarm INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static DataBaseAlarm getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataBaseAlarm.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DataBaseAlarm.class,
                            "alarm_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
