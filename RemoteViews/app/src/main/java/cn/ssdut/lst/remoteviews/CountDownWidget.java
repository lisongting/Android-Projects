package cn.ssdut.lst.remoteviews;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by lisongting on 2017/11/20.
 */

public class CountDownWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        log("onEnabled()");

        Intent serviceIntent = new Intent(context, UpdateService.class);
        context.startService(serviceIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        log("onReceive()");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        log("onUpdate()");

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        log("onAppWidgetOptionsChanged()");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        log("onDeleted()");

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        log("onDisabled()");
        context.stopService(new Intent(context,UpdateService.class));
    }

    private void log(String s) {
        Log.i("CountDownWidget", s);
    }



}

