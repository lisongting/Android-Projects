package cn.ssdut.lst.remoteviews;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lisongting on 2017/10/28.
 */

//创建一个自动更新桌面组件的Service
public class TimeService extends Service {

    private Timer t ;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("tag", "TimeService Created()");
        t = new Timer();
        TimerTask task  = new TimerTask() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(new Date());

                //将时间传给RemoteView
                RemoteViews rv = new RemoteViews(getPackageName(),R.layout.time_widget);
                rv.setTextViewText(R.id.textview,time);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                ComponentName componentName = new ComponentName(getApplicationContext(), TimeWidget.class);
                //将这个RemoteView传过去,让其更新
                appWidgetManager.updateAppWidget(componentName,rv);
            }
        } ;
        t.schedule(task, 0, 1000);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        t.cancel();
        t = null;

    }
}
