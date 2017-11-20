package cn.ssdut.lst.remoteviews;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lisongting on 2017/10/28.
 */

public class TimeWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        log("onEnabled()");
        super.onEnabled(context);
        Intent intent = new Intent(context, TimeService.class);
        context.startService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        log("onReceive()");
        super.onReceive(context, intent);
    }

    //刷新
    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
        log("onUpdate");
        super.onUpdate(context, manager, appWidgetIds);

    }

    //组件从屏幕移除
    @Override
    public void onDisabled(Context context) {
        log("onDisabled()");
        Intent intent = new Intent(context, TimeService.class);
        context.stopService(intent);
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        log("onDeleted()");
        super.onDeleted(context,appWidgetIds);
    }




    private static void log(String s) {
        Log.i("TimeWidget", s);
    }
}
