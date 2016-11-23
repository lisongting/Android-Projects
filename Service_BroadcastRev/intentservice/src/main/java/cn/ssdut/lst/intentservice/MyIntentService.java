package cn.ssdut.lst.intentservice;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Administrator on 2016/10/29.
 */
public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    //IntentService会执行单独的线程来实现该方法
    protected void onHandleIntent(Intent intent) {
        long endTime = System.currentTimeMillis()+20*1000;
        System.out.println("onStartCommand");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try{
                    wait(endTime-System.currentTimeMillis());
                }catch(Exception e){
                }
            }
        }
        System.out.println("耗时任务完成");
    }
}
