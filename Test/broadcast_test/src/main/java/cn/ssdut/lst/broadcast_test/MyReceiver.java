package cn.ssdut.lst.broadcast_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/7.
 */
public class MyReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent ){
        if(Intent.ACTION_BATTERY_CHANGED==intent.getAction()){
            Toast.makeText(context, "battery changed", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context,"MyReceiver---onReceive()",Toast.LENGTH_LONG).show();
        Log.d("tag","MyReceiver---onReceive()");
    }
}
