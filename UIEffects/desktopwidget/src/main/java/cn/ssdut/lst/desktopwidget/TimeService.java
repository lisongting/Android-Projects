package cn.ssdut.lst.desktopwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import layout.NewAppWidget;

/**
 * Created by Administrator on 2017/3/25.
 */

public class TimeService extends Service {
    private Timer timer;
    private SimpleDateFormat sdf;
    public void onCreate(){
        super.onCreate();
        timer = new Timer();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timer.schedule(new TimerTask() {
            public void run() {
                String time = sdf.format(new Date());
                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.new_app_widget);
                remoteViews.setTextViewText(R.id.id_tv_show,time);
                AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                ComponentName cn = new ComponentName(getApplicationContext(), NewAppWidget.class);
                manager.updateAppWidget(cn,remoteViews);
            }
        }, 0, 1000);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
