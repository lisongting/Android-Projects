package com.test.administrator.handlertest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int curImage=0;
    int[] images = {R.drawable.apple,R.drawable.banana,R.drawable.carroit,R.drawable.cherry};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageview = (ImageView)findViewById(R.id.imageview);
        final Handler myHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==555){
                    //动态显示图片
                    imageview.setImageResource(images[curImage++ %images.length]);
                }
            }
        };
        //定义一个Timer,相当于启动了一个新的线程
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(555);
            }
        },0,1200);
    }
}
