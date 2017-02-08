package cn.ssdut.lst.foregroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/8.
 */

public class ForeGroundService extends Service {
    @Override
    public void onCreate() {
        Log.i("tag","--onCreate--");
        super.onCreate();


    }

    @Override
    public void onDestroy() {
        Log.i("tag","--onDestroy--");
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag","--onStartCommand--");
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent t = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, t, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("这是标题")
                .setContentText("这是内容信息")
                .setSmallIcon(R.drawable.notify)
                .setWhen(System.currentTimeMillis());//设置该通知发生的时间
        Notification notify = builder.build();
        notify.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(111, notify);
        Log.i("tag","service在运行中");
        return super.onStartCommand(intent, flags, startId);
    }
}
