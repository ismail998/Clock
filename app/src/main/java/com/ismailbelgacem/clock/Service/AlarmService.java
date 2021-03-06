package com.ismailbelgacem.clock.Service;

import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.RINGMATH;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.RINGOFF;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.TITLE;
import static com.ismailbelgacem.clock.Application.app.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ismailbelgacem.clock.R;
import com.ismailbelgacem.clock.Ui.RaingActivity;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RaingActivity.class);
        notificationIntent.putExtra(RINGMATH,intent.getBooleanExtra(RINGMATH,false));
        notificationIntent.putExtra(RINGOFF,intent.getBooleanExtra(RINGOFF,false));
        Log.d("TAG", "onStartCommand: "+intent.getBooleanExtra(RINGOFF,false) );
        notificationIntent.setAction(String.valueOf(intent.getBooleanExtra(RINGOFF,false)));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        String alarmTitle = String.format("%s Alarm", intent.getStringExtra(TITLE));
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
                .setContentIntent(pendingIntent)
                .build();
        mediaPlayer.start();
        long[] pattern = { 0, 100, 1000 };
        vibrator.vibrate(pattern, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground(pendingIntent);
        else
        startForeground(1, notification);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        vibrator.cancel();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void startMyOwnForeground(PendingIntent pendingIntent){
        String channelName = "My Background Service";
        NotificationChannel chan = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
                .setContentTitle("The phone rings")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(2, notification);}}
}
