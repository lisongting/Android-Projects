package cn.ssdut.lst.remoteviews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by lisongting on 2017/4/23.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String CLICK_ACTION = "cn.lst.action.click";
    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context,intent);
        Log.i("tag", "onReceive()...action:" + intent.getAction());

        if (intent.getAction() == CLICK_ACTION) {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for(int i=0;i<37;i++) {
                        float degree = (i * 10) % 360;
                        //创建RemoteViews对象
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                        Log.i("tag", "packageName:"+context.getPackageName());

                        remoteViews.setImageViewBitmap(R.id.id_iv,rotateBitmap(context,srcBitmap,degree));

                        Intent clickIntent = new Intent();
                        clickIntent.setAction(CLICK_ACTION);

                        //构造一个PendingIntent对象。相当于sendBroadcast()
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
                        //设置点击事件触发的PendingIntent
                        remoteViews.setOnClickPendingIntent(R.id.id_iv, pendingIntent);

                        //这里等于是，将RemoteViews与桌面组件的类MyAppWidgetProvider关联起来
                        manager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),remoteViews);

                        SystemClock.sleep(30);

                    }
                }
            }).start();
        }

    }

    //每次桌面小组件更新时都调用一次该方法
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);
        Log.i("tag", "onUpdate()");
        final int counter = appWidgetIds.length;
        Log.i("tag", "counter:" + counter);
        for(int i=0;i<counter;i++) {
            int appWidgetId = appWidgetIds[i];
            //更新
            update(context, appWidgetManager, appWidgetId);
        }


    }

    private void update(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i("tag", "appWidgetId=" + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        //桌面小组件点击事件发送Intent广播
        Intent clickIntent = new Intent(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.id_iv, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap src, float degree) {
        Matrix matrix = new Matrix();
//        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bitmap;
    }

}
