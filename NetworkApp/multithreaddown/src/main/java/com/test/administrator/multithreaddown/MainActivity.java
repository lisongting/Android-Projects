package com.test.administrator.multithreaddown;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    EditText url;
    EditText target;
    Button downBn;
    ProgressBar bar;
    DownUtil downUtil;
    private int mDownStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = (EditText) findViewById(R.id.url);
        target = (EditText) findViewById(R.id.target);
        downBn = (Button) findViewById(R.id.down);
        bar = (ProgressBar) findViewById(R.id.bar);
        // 创建一个Handler对象
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if (msg.what == 123)
                {
                    bar.setProgress(mDownStatus);
                }
            }
        };
        downBn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                downUtil = new DownUtil(url.getText().toString(),target.getText().toString(),6);
                new Thread(){
                    public void run(){
                        try{
                            downUtil.downLoad();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        final Timer timer = new Timer();
                        timer.schedule(new TimerTask(){
                            public void run(){
                                double completeRate = downUtil.getCompleteRate();
                                mDownStatus = (int)(completeRate*100);
                                handler.sendEmptyMessage(123);
                                if(mDownStatus>=100) {
                                    timer.cancel();
                                    Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },0,100);//定义每秒种调度获取一次系统的完成进度
                    }
                }.start();

            }
        });

    }
}
