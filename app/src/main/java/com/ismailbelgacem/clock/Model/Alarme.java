package com.ismailbelgacem.clock.Model;

import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.FRIDAY;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.MONDAY;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.RECURRING;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.RINGMATH;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.RINGOFF;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.SATURDAY;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.SUNDAY;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.THURSDAY;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.TITLE;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.TUESDAY;
import static com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver.WEDNESDAY;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ismailbelgacem.clock.AlarmBroadcastReceiver.AlarmBroadcastReceiver;
import com.ismailbelgacem.clock.Utils.DayUtil;

import java.util.Calendar;

@Entity(tableName = "alarm_table")
public class Alarme {
    @PrimaryKey
    private int alarmId;

    private int hour, minute;
    private boolean started, recurring;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private  boolean ringOff,ringMath;
    private String title;
    private long created;

    public Alarme(int alarmId, int hour, int minute, boolean started, boolean recurring, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean ringOff, boolean ringMath, String title, long created) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.started = started;
        this.recurring = recurring;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.ringOff = ringOff;
        this.ringMath = ringMath;
        this.title = title;
        this.created = created;
    }

    public boolean isRingOff() {
        return ringOff;
    }

    public void setRingOff(boolean ringOff) {
        this.ringOff = ringOff;
    }

    public boolean isRingMath() {
        return ringMath;
    }

    public void setRingMath(boolean ringMath) {
        this.ringMath = ringMath;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;
        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }
    public String getRecurringDaysText() {
        if (!recurring) {
            return null;
        }

        String days = "";
        if (monday) {
            days += "Mo ";
        }
        if (tuesday) {
            days += "Tu ";
        }
        if (wednesday) {
            days += "We ";
        }
        if (thursday) {
            days += "Th ";
        }
        if (friday) {
            days += "Fr ";
        }
        if (saturday) {
            days += "Sa ";
        }
        if (sunday) {
            days += "Su ";
        }

        return days;
    }
    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);
        intent.putExtra(TITLE, title);
        intent.putExtra(RINGMATH,ringMath);
        intent.putExtra(RINGOFF,ringOff);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute, alarmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }

}
