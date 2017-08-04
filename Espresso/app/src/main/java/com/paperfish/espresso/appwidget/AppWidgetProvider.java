package com.paperfish.espresso.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.paperfish.espresso.R;

/**
 * Created by lisongting on 2017/8/4.
 */

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
    public static final String REFRESH_ACTION = "com.paperfish.espresso.action.refresh";
    public static final String TAG = "AppWidgetProvider";

    public static Intent getRefreshBroadcastIntent(Context context) {
        return new Intent(REFRESH_ACTION).setComponent(new ComponentName(context, AppWidgetProvider.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        log(TAG + " -- onUpdate");
        for (int id : appWidgetIds) {
            RemoteViews remoteViews = updateWidgetListView(context, id);
            appWidgetManager.updateAppWidget(id, remoteViews);
        }

        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (action.equals(REFRESH_ACTION)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context, AppWidgetProvider.class);
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(name), R.id.listViewWidget);

        }
    }

    private RemoteViews updateWidgetListView(Context context, int id) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.launcher_list_widget);

        Intent intent = new Intent(context, AppWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        //这里把这个intent序列化为uri，不知道是什么目的
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(R.id.listViewWidget, intent);
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.emptyView);

        Intent tmpIntent = new Intent(context, PackagesDetailsActivity.class);
        tmpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //?
        remoteViews.setPendingIntentTemplate(R.id.listViewWidget,
                PendingIntent.getActivity(context, 0, tmpIntent, PendingIntent.FLAG_CANCEL_CURRENT));

        return remoteViews;
    }


    public void log(String str) {
        Log.i(this.getClass().getSimpleName(), str);
    }


}
