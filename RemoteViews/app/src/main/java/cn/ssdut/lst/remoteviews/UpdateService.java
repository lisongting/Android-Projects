package cn.ssdut.lst.remoteviews;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时桌面控件
 * Created by lisongting on 2017/11/20.
 *
 */

public class UpdateService extends Service{
    private Context context;
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        log("onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("onStartCommand()");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //计算剩余的天数
                Calendar currentCalendar = Calendar.getInstance();
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.set(Calendar.YEAR, 2018);
                endCalendar.set(Calendar.MONTH, 0);
                endCalendar.set(Calendar.DAY_OF_MONTH, 30);
                endCalendar.set(Calendar.HOUR_OF_DAY, 17);
                long remainTimeInMillis = endCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();
                int remainDays = (int) (remainTimeInMillis / (24 * 60 * 60 * 1000));



                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.count_down_widget);
                remoteViews.setTextViewText(R.id.counter, String.valueOf(remainDays));
                remoteViews.setTextColor(R.id.counter, Color.parseColor("#209cff"));

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                remoteViews.setOnClickPendingIntent(R.id.counter, pendingIntent);

                AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                ComponentName componentName = new ComponentName(getApplicationContext(), CountDownWidget.class);
                manager.updateAppWidget(componentName,remoteViews);

            }
        }, 0, 4*60*60*1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
        log("onDestroy()");

    }

    private void log(String s) {
        Log.i("UpdateService:", s);
    }
}
