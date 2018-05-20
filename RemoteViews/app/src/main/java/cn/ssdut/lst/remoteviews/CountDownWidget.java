package cn.ssdut.lst.remoteviews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

/**
 * Created by lisongting on 2017/11/20.
 */

public class CountDownWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        log("onEnabled()");
        super.onEnabled(context);

        Intent serviceIntent = new Intent(context, UpdateService.class);
        context.startService(serviceIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        log("onReceive()");
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        log("onUpdate()");
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        //本来在Service中就写了界面更新的逻辑，这里再写是因为当添加了多个相同的CountDownWidget组件时，
        //尽管Service在后台运行，但Service中的定时器不会马上触发界面更新操作(我设置其更新频率为12小时)
        //当每添加一个新的CountDownWidget时，会触发onReceive -> onUpdate，所以在onUpdate中再更新一下界面
        //计算剩余的天数
        Calendar currentCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.YEAR, Constant.destYear);
        endCalendar.set(Calendar.MONTH, Constant.destMonth);
        endCalendar.set(Calendar.DAY_OF_MONTH, Constant.destDay);
        endCalendar.set(Calendar.HOUR_OF_DAY, Constant.destHour);
        long remainTimeInMillis = endCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();
        int remainDays = (int) (remainTimeInMillis / (24 * 60 * 60 * 1000));



        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.count_down_widget);
        remoteViews.setTextViewText(R.id.counter, String.valueOf(remainDays));
        remoteViews.setTextColor(R.id.counter, Color.parseColor("#209cff"));

        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, 0);

        remoteViews.setOnClickPendingIntent(R.id.counter, pendingIntent);

        AppWidgetManager manager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName componentName = new ComponentName(context.getApplicationContext(), CountDownWidget.class);
        manager.updateAppWidget(componentName,remoteViews);

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        log("onAppWidgetOptionsChanged()");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        log("onDeleted()");
        super.onDeleted(context, appWidgetIds);

    }

    @Override
    public void onDisabled(Context context) {
        log("onDisabled()");
        super.onDisabled(context);
        context.stopService(new Intent(context,UpdateService.class));
    }

    private void log(String s) {
        Log.i("CountDownWidget", s);
    }



}

