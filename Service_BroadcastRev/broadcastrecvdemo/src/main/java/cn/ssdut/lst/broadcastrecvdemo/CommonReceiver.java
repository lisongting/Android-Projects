package cn.ssdut.lst.broadcastrecvdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/7.
 */
public class CommonReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("tag","CommonReceiver接收到普通广播");
        String str = intent.getStringExtra("key");
        Log.i("tag", "广播传递的信息为：" + str);
    }
}
