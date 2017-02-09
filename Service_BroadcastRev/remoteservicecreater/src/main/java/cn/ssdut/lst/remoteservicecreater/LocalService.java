package cn.ssdut.lst.remoteservicecreater;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/9.
 */

public class LocalService extends Service {
    private int count=0;
    private boolean quit=false;
    private MyBinder binder;

    public class MyBinder extends Binder{
        public int getCount(){
            return count;
        }
    }
    //在该Service中启动一个计数器
    public void onCreate() {
        binder = new MyBinder();
        Log.i("tag","LocalService----onCreate()");
        new Thread() {
            public void run() {
                while (!quit) {
                    try{
                        count++;
                        Thread.sleep(1000);
                    } catch (Exception e){
                      e.printStackTrace();
                    }
                }
            }
        }.start();
        super.onCreate();
    }
    public int onStartCommand(Intent intent,int flags,int startId) {
        Log.i("tag","LocalService----onStartCommand()");
        return super.onStartCommand(intent,flags,startId);
    }
    public IBinder onBind(Intent intent) {
        Log.i("tag","LocalService----onBind()");
        return binder;
    }
    public boolean onUnbind(Intent intent) {
        Log.i("tag","LocalService----onUnbind()");
        super.onUnbind(intent);
        return true;
    }
    public void onDestroy() {
        this.quit=true;
        Log.i("tag","LocalService----onDestroy()");
        super.onDestroy();
    }

}
