package cn.ssdut.lst.broadcastrecvdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MyBroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("cn.ssdut.lst.Broadcast");
        intent.putExtra("msg","简单的消息");
        //发送无序广播
        sendBroadcast(intent);
        //发送有序广播
        sendOrderedBroadcast(intent,null);
        //registerReceiver(intent);

    }
    protected void onDestroy() {
        unregisterReceiver(receiver);
    }
}
