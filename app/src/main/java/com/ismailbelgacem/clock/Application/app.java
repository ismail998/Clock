package com.ismailbelgacem.clock.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class app extends Application {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    Context context =null;
    @Override
    public void onCreate() {
        super.onCreate();
       context = getApplicationContext();
        createNotificationChannnel();
    }

    public Context getContext() {
        return context;
    }

    private void createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
