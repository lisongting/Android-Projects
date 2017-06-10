package com.example.rostest.lst_try;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rostest.PublishEvent;
import com.example.rostest.ros.ROSClient;
import com.example.rostest.ros.rosbridge.ROSBridgeClient;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by lisongting on 2017/6/4.
 */
//Ros服务器和Service和Activity的交互机制试验
public class RosConnectionService extends Service {

//    public static final String TAG = "RosConnectionService";
    public static final String TAG = "tag";
    public static final String PUBLISH_TOPIC = "/tts_status";
    public static final String SUBSCRIBE_TOPIC = "/museum_position";
    public static final String TEST_TOPIC = "";

    public ROSBridgeClient rosBridgeClient;
    private boolean isConnected;

    private myBinder myBinder = new myBinder();
    public class myBinder extends Binder {

        public boolean connect(){
            return isConnected;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "RosConnService--onCreate()");
        //注册Eventbus
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "RosConnService--onStartCommand()");


        //订阅Ros即将发布的topic
//        JSONObject strSubscribe = new JSONObject();
//        try {
//            strSubscribe.put("op", "subscribe");
//            strSubscribe.put("topic", SUBSCRIBE_TOPIC);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        rosBridgeClient.send(strSubscribe.toString());

        return super.onStartCommand(intent, flags, startId);


    }

    //订阅某个topic后，接收到Ros服务器返回的message，回调此方法
    public void onEvent(PublishEvent event) {
        //topic的名称
        String topicName = event.name;

        Log.i("tag", "Service 接收到event:" + event.toString());
        String msg = event.msg;
        JSONObject msgInfo ;
        try {
            msgInfo = new JSONObject(msg);
            Log.i("tag", "onEvent:" + event.msg);
            // EventBus.getDefault().post(new MovebaseStatus(666,false));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "RosConnService--onDestroy()");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("tag", "RosConnectionService onBind()");
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
                        Log.i("tag","ConnectionStatusListener--onConnect");

                    }
                    @Override
                    public void onDisconnect(boolean normal, String reason, int code) {
                        Log.i("tag","ConnectionStatusListener--disconnect");
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                        Log.i("tag","ConnectionStatusListener--ROS communication error");
                    }
                });
                if (conn) {
                    //订阅Ros即将发布的topic
//                    JSONObject strSubscribe = new JSONObject();
//                    try {
//                        strSubscribe.put("op", "subscribe");
//                        strSubscribe.put("topic", SUBSCRIBE_TOPIC);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    rosBridgeClient.send(strSubscribe.toString());
                    Log.i("tag", "RosConnectionService连接Ros成功");
                } else {
                    Log.i("tag", "RosConnectionService连接Ros失败");
                }
                isConnected = conn;
            }
        }.run();
        return myBinder;
    }

}
