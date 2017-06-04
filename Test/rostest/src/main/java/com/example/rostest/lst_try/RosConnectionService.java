package com.example.rostest.lst_try;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.rostest.ros.rosbridge.ROSBridgeClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lisongting on 2017/6/4.
 */

public class RosConnectionService extends Service {

    public static final String TAG = "RosConnectionService";
    public static final String PUBLISH_TOPIC = "/tts_status";
    public static final String SUBSCRIBE_TOPIC = "/museum_position";
    private Handler handler;
    private ROSBridgeClient rosBridgeClient;
    private myBinder binder;

    //内部类myBinder
    public class myBinder extends Binder{

        //为该Sevice设置handler
        public void setHandler(Handler h){
            handler = h;
        }

        public void setRosBridgeClient(ROSBridgeClient roslient){
            rosBridgeClient = roslient;
        }
        public void sendTTsStatus(TtsStatus ttsStatus){
            sendPlayingStatus(ttsStatus);
        }

    }


//    public RosConnectionService(Handler handler,ROSBridgeClient rosBridgeClient) {
//        this.handler = handler;
//        this.rosBridgeClient = rosBridgeClient;
//    }

    //不断地发布TTS的状态
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

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "RosConnService--onCreate()");

        //订阅Ros即将发布的topic
        JSONObject strSubscribe = new JSONObject();
        try {
            strSubscribe.put("op", "subscribe");
            strSubscribe.put("topic", SUBSCRIBE_TOPIC);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rosBridgeClient.send(strSubscribe.toString());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "RosConnService--onStartCommand()");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "RosConnService--onDestroy()");
        handler.sendEmptyMessage(666);
        //取消订阅
        JSONObject strSubscribe = new JSONObject();
        try {
            strSubscribe.put("op", "unsubscribe");
            strSubscribe.put("topic", SUBSCRIBE_TOPIC);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rosBridgeClient.send(strSubscribe.toString());

        rosBridgeClient.disconnect();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "RosConnService--onUnbind()");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "RosConnService--onBind()");
        return binder;
    }
}
