package cn.ssdut.lst.intentservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrator on 2016/10/29.
 */
public class MyService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //该方法内执行耗时任务可能会造成ANR问题
        long endTime = System.currentTimeMillis()+20*1000;
        System.out.println("onStart");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime-System.currentTimeMillis());
                } catch (Exception e) {
                }
            }
        }
        System.out.println("耗时任务完成----");
        return START_STICKY;
    }
}
