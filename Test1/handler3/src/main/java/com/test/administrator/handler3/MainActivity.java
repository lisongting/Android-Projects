package com.test.administrator.handler3;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button bt;
    private TextView textView;
    private Thread sonThread;
    private Handler mhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);
        sonThread = new myThread();
        sonThread.start();
        bt.setOnClickListener(new ButtonListener());
    }

    class myThread extends Thread{

        public void run(){
            Looper.prepare();//第一步，准备Looper对象
            //第二步，创建Handler子类实例，重写handMessage方法
            mhandler = new Handler(){
                @Override
                public void handleMessage(Message msg){
                    if(msg.what==123){
                        String s = new String("从主线程往子线程发送消息，在子线程中定义Handler的" +
                                "handMessage方法，在子线程中对message进行处理\n注意：不能在子线程中对UI界面进行更改，否则将会闪退！");
                        //textView.setText(s);
                        //textView.setTextColor(Color.GREEN);
                        Toast.makeText(MainActivity.this,
                                "当前子线程是："+Thread.currentThread().getName()+"\n说明:"+s,Toast.LENGTH_LONG).show();
                    }
                }
            };
            //第三步，调用loop方法，之后looper会不断的从消息队列中取出消息对象，直到消息队列为空，则导致线程阻塞
            Looper.loop();
        }
    }
    class ButtonListener implements View.OnClickListener{
        public void onClick(View v){
            //Message msg = new Message();
            // 貌似不能使用New Message的形式，只能使用Message msg = mhandler.obtainMessage()的方式
            Message msg = mhandler.obtainMessage();
            msg.what=123;
            mhandler.sendMessage(msg);
        }

    }
}
