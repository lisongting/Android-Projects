package cn.ssdut.lst.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void send(View v){
        Intent intent = new Intent();
        intent.setAction("cn.ssdut.lst.BROADCAST");
        intent.putExtra("msg","简单的消息");
        sendBroadcast(intent);
    }
}
