package com.test.administrator.multithreadclient;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Administrator on 2016/9/30.
 * 客户端线程,用来与服务器进行数据交换
 */
public class ClientThread implements Runnable {
    private Socket s ;
    public Handler revHandler;//该handler用来接受
    private BufferedReader br=null;
    private OutputStream os=null;
    public ClientThread(Handler h){
        this.revHandler = h;
    }
    public void run(){
        try{
            s = new Socket("10.21.221.81",30000);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            //启动另一条线程来读取服务器响应的数据
            new Thread(){
                public void run(){
                    String content = null;
                    try{
                        while((content=br.readLine())!=null){
                            //每当读取到来自服务器的数据后,发送message 给主线程去更新UI
                            Message msg = new Message();
                            //Message msg =revHandler.obtainMessage();
                            msg.obj = content;
                            msg.what=123;
                            MainActivity.handler.sendMessage(msg);
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();



            //在子线程中定义Handler 要初始化Looper
            Looper.prepare();
            revHandler = new Handler(){
                public void handleMessage(Message msg){
                    if(msg.what==345){//接受到来自用户的发送请求时,向服务器发送数据
                        try{
                            os.write((msg.obj.toString()+"\n").getBytes("utf-8"));
                        }catch(IOException e){
                            e.printStackTrace();
                        }

                    }
                }
            };
            Looper.loop();
        }catch(SocketTimeoutException e){
            //Toast.makeText(Context,"服务器请求超时",Toast.LENGTH_SHORT).show();
            Log.d("tag","服务器请求超时");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
