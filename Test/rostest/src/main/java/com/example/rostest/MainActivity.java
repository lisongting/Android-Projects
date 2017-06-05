package com.example.rostest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rostest.lst_try.MovebaseStatus;
import com.example.rostest.lst_try.RosConnectionService;
import com.example.rostest.lst_try.TtsStatus;
import com.example.rostest.ros.ROSClient;
import com.example.rostest.ros.rosbridge.ROSBridgeClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

import static com.example.rostest.lst_try.RosConnectionService.PUBLISH_TOPIC;
import static com.example.rostest.lst_try.RosConnectionService.SUBSCRIBE_TOPIC;

//Ros服务器和Service和Activity的交互机制试验
public class MainActivity extends AppCompatActivity {
    ROSBridgeClient rosBridgeClient;
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            Bundle data = msg.getData();
//            int position = data.getInt("positionId");
//            boolean isMoving = data.getBoolean("isMoving");
            if (msg.what == 6666) {
                Log.i("tag", "get a message from Service:" + msg.what);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, RosConnectionService.class);
        startService(intent);
    }

    public void onEvent(MovebaseStatus status) {
        Log.i("tag", "MovebaseStatus:" + status.toString());
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
                        Log.i("tag","Connect ROS success");

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
                    Toast.makeText(MainActivity.this, "连接Ros成功！", Toast.LENGTH_SHORT).show();

                    //订阅Ros即将发布的topic
                    JSONObject strSubscribe = new JSONObject();
                    try {
                        strSubscribe.put("op", "subscribe");
                        strSubscribe.put("topic", SUBSCRIBE_TOPIC);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rosBridgeClient.send(strSubscribe.toString());
                    Log.i("tag", "连接Ros成功");

                    TimerTask publishTtsTask = new TimerTask(){

                        @Override
                        public void run() {
                            //取消订阅
                            JSONObject strSubscribe = new JSONObject();
                            try {
                                strSubscribe.put("op", "unsubscribe");
                                strSubscribe.put("topic", SUBSCRIBE_TOPIC);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            rosBridgeClient.send(strSubscribe.toString());

//                            rosBridgeClient.disconnect();
                        }
                    };

                    Timer timer = new Timer();
//                    timer.schedule(publishTtsTask,2000,3000);
                } else {
                    Log.i("tag", "连接Ros失败");

                }
            }
        }.run();

    }

    //不断地发布播放器的状态
    public void sendPlayingStatus(TtsStatus status){
        Log.i("tag", status.toString());
        JSONObject jsonStatus = new JSONObject();
        try {
            jsonStatus.put("id", status.getId());
            jsonStatus.put("playing", status.isplaying());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("op", "publish");
            jsonObject.put("topic",PUBLISH_TOPIC);
            jsonObject.put("msg", jsonStatus.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rosBridgeClient.send(jsonObject.toString());

    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
