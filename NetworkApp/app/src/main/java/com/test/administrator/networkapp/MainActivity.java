package com.test.administrator.networkapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        new Thread(){
            public void run(){
                try{
                    Socket socket = new Socket("10.21.221.81",30000);
                    InputStreamReader ip = new InputStreamReader(socket.getInputStream());
                    BufferedReader br = new BufferedReader(ip);
                    String line = br.readLine();
                    textView.setText("接收到数据:"+line);
                    Log.d("tag","接收到数据:"+line);
                    br.close();
                    ip.close();
                    socket.close();
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
