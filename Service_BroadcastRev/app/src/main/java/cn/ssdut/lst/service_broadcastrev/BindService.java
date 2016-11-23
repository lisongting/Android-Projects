package cn.ssdut.lst.service_broadcastrev;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2016/10/27.
 */
public class BindService extends Service {
    private int count;
    private boolean quit;
    private MyBinder binder = new MyBinder();
    public class MyBinder extends Binder{
        public int getCount(){
            return count;
        }
    }

    public IBinder onBind(Intent intent) {
        Log.d("tag", "Service is Binded ");
        return binder;
    }
    public void onCreate(){
        super.onCreate();
        Log.d("tag", "Service is Created");
        //启动一条线程，动态地修改count的值
        new Thread(){
            public void run(){
                while(!quit){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }.start();
    }

    //service被断开连接时回调该方法
    public boolean onUnbind(Intent intent){
        Log.d("tag", "Service is Unbinded");
        return true;
    }

    //service被关闭之前回调该方法
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        Log.d("tag", "service is destroyed");
    }
}
