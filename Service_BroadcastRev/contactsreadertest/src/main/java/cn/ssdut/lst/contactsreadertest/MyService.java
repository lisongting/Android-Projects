package cn.ssdut.lst.contactsreadertest;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MyService extends Service {
    private Thread sendThread;
    private Timer timer ;
    public IBinder onBind(Intent intent){
        Log.d("tag","--------MyService--onBind()");
        return null;
    }
    public void onCreate(){
        super.onCreate();
        Log.d("tag","--------MyService--onCreate()");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tag","--------MyService--onStartCommand()");
        sendThread = new SendThread();
        sendThread.start();
        return START_STICKY;

    }
    public void onDestroy(){
        super.onDestroy();
        timer.cancel();
        Log.d("tag","--------MyService--onDestroy()");

    }

    class SendThread extends Thread{
        public void run(){
            //服务关闭后TimerTask还会继续运行,因为Timer.schedule会开启新的线程
            timer  = new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run() {
                    Log.d("tag","--------MyService正在运行");
                }
            },0,2000);
        }
        PendingIntent intent;
    }
}
