package com.paperfish.espresso.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.paperfish.espresso.service.ReminderService;

import java.util.Calendar;

/**
 * Created by lisongting on 2017/8/11.
 */

public class PushUtil {

    public static final String TAG = PushUtil.class.getSimpleName();

    public static void startAlarmService(Context context, Class<?> service, long interval) {
        boolean alert = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SettingsUtil.KEY_ALERT, true);

        if (alert) {
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, service);
            PendingIntent pendingIntent = PendingIntent.getService(context, 10000, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0, interval, pendingIntent);
            Log.i(TAG, "startAlarmService");
        }
    }

    public static void stopAlarmService(Context context,Class<?> service){
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, service);
        PendingIntent pendingIntent = PendingIntent.getService(context, 10000,intent, PendingIntent.FLAG_CANCEL_CURRENT);
        manager.cancel(pendingIntent);
        Log.i(TAG, "stopAlarmService");
    }

    public static void startReminderService(Context context) {

        //默认为30分钟
        int intervalTime = getIntervalTime(Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SettingsUtil.KEY_NOTIFICATION_INTERVAL, "1")));
        if (intervalTime > -1) {
            startAlarmService(context, ReminderService.class, intervalTime);
            Log.i(TAG, "startReminderService ,intervalTime:" + intervalTime);
        }

    }


    public static void stopRemiderService(Context context) {
        stopAlarmService(context, ReminderService.class);
    }

    public void restartRemiderService(Context context) {
        stopRemiderService(context);
        startReminderService(context);
    }


    public static boolean isInDisturbTime(Context context, Calendar calendar) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int startHour = sp.getInt(SettingsUtil.KEY_DO_NOT_DISTURB_MODE_START_HOUR, 23);
        int startMinute = sp.getInt(SettingsUtil.KEY_DO_NOT_DISTURB_MODE_START_MINUTE, 0);
        int endHour = sp.getInt(SettingsUtil.KEY_DO_NOT_DISTURB_MODE_END_HOUR, 6);
        int endMinute = sp.getInt(SettingsUtil.KEY_DO_NOT_DISTURB_MODE_END_MINUTE, 0);
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        int nowMinute = calendar.get(Calendar.MINUTE);
        return (nowHour >= startHour && nowMinute >= startMinute) && (nowHour <= endHour && nowMinute <= endMinute);
    }

    public static int getIntervalTime(int id) {
        switch (id) {
            case 0:
                return 10 * 60 * 1000; // 10 minutes
            case 1:
                return 30 * 60 * 1000; // 30 minutes
            case 2:
                return 60 * 60 * 1000; // 1 hour
            case 3:
                return 90 * 60 * 1000; // 1.5 hours
            case 4:
                return 120 * 60 * 1000; // 2 hours
            default:
                return -1;
        }
    }
}
