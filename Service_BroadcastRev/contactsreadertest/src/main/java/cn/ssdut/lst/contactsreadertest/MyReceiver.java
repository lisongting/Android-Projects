package cn.ssdut.lst.contactsreadertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/30.
 */
public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context,Intent intent){
        Log.d("tag","MyReceiver-----onCreate()调用");
        Intent tIntent = new Intent(context,MyService.class);
        context.startService(tIntent);
    }
}
