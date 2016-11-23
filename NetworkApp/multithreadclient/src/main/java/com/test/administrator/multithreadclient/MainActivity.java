package com.test.administrator.multithreadclient;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText input;
    //Button bt;
    TextView show;
    public static Handler handler;
    ClientThread clientThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText)findViewById(R.id.editText);
        show = (TextView)findViewById(R.id.textView);
        //启动子线程与服务器建立连接
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==123){//如果接受到来自子线程的123Message,则更新UI
                    show.append("\n"+msg.obj.toString());
                    Log.d("tag","收到来自客户端的数据:"+msg.obj.toString());
                }
            }
        };
    }
    public void send(View source){
        try{
            Message msg = new Message();
            //Message msg = handler.obtainMessage();
            msg.what=345;//发送数据的Message编号345
            msg.obj= input.getText().toString();
            clientThread.revHandler.sendMessage(msg);
            input.setText("");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
