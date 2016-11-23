package com.test.administrator.handler2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//从子线程发送消息到主线程，由主线程对消息进行处理
public class MainActivity extends AppCompatActivity {
    private Button bt;
    private TextView textView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new myHandler();
        bt = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);

        //为button绑定事件监听器，点击button时启动新的线程
        bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Thread thread1 = new myThread();
                thread1.start();
            }
        });
    }

    class myHandler extends Handler{
        //实质上是在主线程中对消息进行处理
        public void handleMessage(Message msg){
            if(msg.what==11){
                String temp = (String)msg.obj;
                textView.setText(temp);
                textView.setTextColor(Color.BLUE);
                Toast.makeText(MainActivity.this,"当前线程名："+Thread.currentThread().getName(),Toast.LENGTH_LONG).show();
            }

        }
    }

    class myThread extends Thread{
        public void run(){
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
            String s = new String("这个程序中，由子线程发送消息给主线程，由主线程处理Message,因为是主线程处理message，" +
                    "所以可以修改这个TextView中的内容");
            Message msg = handler.obtainMessage();
            msg.what = 11;
            msg.obj = s;//
            handler.sendMessage(msg);//在这个线程 中去触发Handler
        }
    }
}
