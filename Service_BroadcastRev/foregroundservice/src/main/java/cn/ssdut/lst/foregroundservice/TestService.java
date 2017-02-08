package cn.ssdut.lst.foregroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/8.
 */

public class TestService extends Service {
    @Override
    public void onCreate() {
        Log.i("tag","onCreate() is called ");
        super.onCreate();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag","onStartCommand() is called ");
        return super.onStartCommand(intent, flags, startId);
    }
    public void onDestroy() {
        Log.i("tag","onDestroy() is called ");
        super.onDestroy();
    }
    public boolean onUnbind(Intent intent) {
        Log.i("tag","onUnbind() is called ");
        return super.onUnbind(intent);
    }
    public IBinder onBind(Intent intent) {
        Log.i("tag","onBind() is called ");
        return null;
    }
}
