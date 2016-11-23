package com.test.administrator.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static final int NOTIFICATION_ID=666;
    NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void send(View source){
        Intent intent = new Intent(MainActivity.this,OtherActivity.class);
        PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,intent,0);
        Notification notify = new Notification.Builder(this)
                .setAutoCancel(true)
                .setTicker("有新消息")
                .setSmallIcon(R.drawable.notify)
                .setContentTitle("一条新通知")
                .setContentText("恭喜你被阿里巴巴成功录用!")
                .setContentIntent(pi).build();
        nm.notify(NOTIFICATION_ID,notify);
    }
    public void cancel(View v){
        nm.cancel(NOTIFICATION_ID);
    }
}
