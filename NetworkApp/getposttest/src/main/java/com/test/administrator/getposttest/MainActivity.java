package com.test.administrator.getposttest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String response;
    TextView show;
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==123){
                show.setText(response);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        show = (TextView) findViewById(R.id.show);
    }
    public void get(View v){
        new Thread(){

            public void run(){
                response = GetPostUtil.sendGet("http://10.21.221.81:8080/abc/a.jsp",null);
                handler.sendEmptyMessage(123);
            }
        } .start();
    }
    public void post(View v){
        new Thread(){
            public void run(){
                response = GetPostUtil.sendPost("http://10.21.221.81:8080/abc/login.jsp","name=crazyit.org&pass=leegang");
                handler.sendEmptyMessage(123);
            }
        } .start();
    }
}
