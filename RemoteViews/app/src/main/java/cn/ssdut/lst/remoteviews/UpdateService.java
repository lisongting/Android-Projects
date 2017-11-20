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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lisongting on 2017/11/20.
 */

public class UpdateService extends Service{
    private long num;
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
                num++;
                log("num:" + num);
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.count_down_widget);
                remoteViews.setTextViewText(R.id.counter, String.valueOf(num));
                remoteViews.setTextColor(R.id.counter, Color.parseColor("#26c6da"));

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                remoteViews.setOnClickPendingIntent(R.id.counter, pendingIntent);

                AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                ComponentName componentName = new ComponentName(getApplicationContext(), CountDownWidget.class);
                manager.updateAppWidget(componentName,remoteViews);
            }
        }, 0, 1000);

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
