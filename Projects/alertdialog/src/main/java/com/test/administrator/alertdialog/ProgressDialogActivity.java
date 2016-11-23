package com.test.administrator.alertdialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by Administrator on 2016/9/21.
 */
public class ProgressDialogActivity extends AppCompatActivity {
    final static int MAX_PROGRESS=100;
    private int[] data = new int[50];
    int progressStatus=0;
    int hasData=0;
    ProgressDialog pd1,pd2;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==222){
                pd2.setProgress(progressStatus);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressdialog_layout);
    }

    public void showSpinner(View source){
        //调用静态方法显示环形进度条
        ProgressDialog.show(this,"任务执行中","任务正在执行，请稍后",false,true);
    }

    public void showIndeterminate(View source){
        pd1 = new ProgressDialog(this);
        pd1.setTitle("任务正在执行中");
        pd1.setMessage("任务正在执行中，请等待");
        pd1.setCancelable(true );
        //设置为横向进度条
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setIndeterminate(true);
        pd1.show();
    }

    public void showProgress(View source){
        progressStatus=0;
        hasData=0;
        pd2 = new ProgressDialog(this);
        pd2.setTitle("任务完成百分比");
        pd2.setMessage("耗时任务完成百分比");
        pd2.setCancelable(false);
        pd2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd2.setIndeterminate(false);
        pd2.show();
        //启动一个线程
        new Thread(){
            public void run(){
                while(progressStatus<MAX_PROGRESS){
                    progressStatus = MAX_PROGRESS*doWork()/data.length;
                    handler.sendEmptyMessage(222);
                }
                if(progressStatus>=MAX_PROGRESS){
                    pd2.dismiss();
                }
            }
        }.start();

    }

    public int doWork(){
        data[hasData++] = (int)(Math.random()*100);
        try {
            Thread.sleep(100);
        }catch(Exception e){
            e.printStackTrace();
        }
        return hasData;
    }
}
