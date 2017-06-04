package com.example.rostest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rostest.lst_try.RosConnectionService;
import com.example.rostest.ros.ROSClient;
import com.example.rostest.ros.rosbridge.ROSBridgeClient;

public class MainActivity extends AppCompatActivity {


    private RosConnectionService.myBinder rosService;
    ROSBridgeClient rosBridgeClient;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int position = data.getInt("positionId");
            boolean isMoving = data.getBoolean("isMoving");

            Toast.makeText(MainActivity.this, "get a message from Service:"+msg.what, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                rosService = (RosConnectionService.myBinder) service;
                rosService.setHandler(handler);
                rosService.setRosBridgeClient(rosBridgeClient);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent intent = new Intent(this, RosConnectionService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void onResume() {
        super.onResume();
        new Thread() {
            public void run() {
                String rosIP = "192.168.1.179";
                String rosPort = "9090";
                String rosURL = "ws://" + rosIP + ":" + rosPort;
                rosBridgeClient = new ROSBridgeClient(rosURL);
                boolean conn = rosBridgeClient.connect(new ROSClient.ConnectionStatusListener() {
                    @Override
                    public void onConnect() {
                        rosBridgeClient.setDebug(true);

                        Log.d("tag","Connect ROS success");
                        try {
                            String[] nodes = rosBridgeClient.getNodes();
                            String[] topics = rosBridgeClient.getTopics();
                            String[] services = rosBridgeClient.getServices();

                            for (String node : nodes) {
                                Log.i("tag", "----node:"+node);
                            }
                            for (String i : topics) {
                                Log.i("tag", "----topic:" + i);
                            }
                            for (String s : services) {
                                Log.i("tag", "----service:" + s);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onDisconnect(boolean normal, String reason, int code) {
                        Log.d("tag","ROS disconnect");
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                        Log.d("tag","ROS communication error");
                    }
                });
                if (conn) {
                    Log.i("tag", "连接成功");
                } else {
                    Log.i("tag", "连接失败");

                }
            }
        }.run();

    }

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("op", "subscribe");
//            jsonObject.put("topic", "/museum_position");
//            Log.i("tag", jsonObject.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
}
