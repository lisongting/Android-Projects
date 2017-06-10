package com.example.rostest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rostest.lst_try.RosConnectionService;

import de.greenrobot.event.EventBus;

//Ros服务器和Service和Activity的交互机制试验
public class MainActivity extends AppCompatActivity {
    public static final String PUBLISH_TOPIC = "/tts_status";
    public static final String SUBSCRIBE_TOPIC = "/museum_position";

    RosConnectionService.myBinder binder;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("tag", "onServiceConnected");
            binder = (RosConnectionService.myBinder) service;
            if(binder.connect()){
                Toast.makeText(MainActivity.this, "MainActivity--连接Ros成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "MainActivity--连接Ros失败", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("tag", "onServiceDisConnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, RosConnectionService.class);
//        startService(intent);

        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    //接受到Service中发过来的event
    public void onEvent(PublishEvent event) {
        Log.i("tag", "MainActivity接受到topic的信息为:" + event.msg);
    }

    public void onResume() {
        super.onResume();

//        TimerTask publishTask = new TimerTask() {
//            @Override
//            public void run() {
//                sendPlayingStatus(new TtsStatus(6, true));
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(publishTask,1000,500);

    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
//    //不断地发布播放器的状态
//    public void sendPlayingStatus(TtsStatus status){
////        Log.i("tag", status.toString());
//        JSONObject jsonStatus = new JSONObject();
//        try {
//            jsonStatus.put("id", status.getId());
//            jsonStatus.put("playing", status.isplaying());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("op", "publish");
//            jsonObject.put("topic",PUBLISH_TOPIC);
//            jsonObject.put("msg", jsonStatus.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        rosBridgeClient.send(jsonObject.toString());
//
//    }




    //                    TimerTask publishTtsTask = new TimerTask(){
//
//                        @Override
//                        public void run() {
//                            //取消订阅
//                            JSONObject strSubscribe = new JSONObject();
//                            try {
//                                strSubscribe.put("op", "unsubscribe");
//                                strSubscribe.put("topic", SUBSCRIBE_TOPIC);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            rosBridgeClient.send(strSubscribe.toString());
//
////                            rosBridgeClient.disconnect();
//                        }
//                    };
}
