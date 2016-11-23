package com.test.administrator.layerdrabletest;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/10/21.
 */
public class ClipDrawActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clip_layout);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        final ClipDrawable drawable = (ClipDrawable)imageView.getDrawable();
        final Handler handler= new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==123)
                    drawable.setLevel(drawable.getLevel()+200);
            }
        };
        //用定时器启动一个定时任务
        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                Message msg = new Message();
                msg.what =123;
                handler.sendMessage(msg);
                if(drawable.getLevel()>=10000){
                    timer.cancel();
                }
            }
        },0,100);
    }
}
