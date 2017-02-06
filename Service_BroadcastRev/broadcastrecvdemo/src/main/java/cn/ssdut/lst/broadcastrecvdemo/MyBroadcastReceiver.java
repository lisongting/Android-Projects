package cn.ssdut.lst.broadcastrecvdemo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Administrator on 2017/2/6.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NetworkInfo netIntfo = null;
        ConnectivityManager cm;
        try {
            cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            netIntfo =  cm.getActiveNetworkInfo();
        } catch (Exception e) {
            //异常处理
            Log.d("tag", "没有网络权限，请给予相关权限");
        }
        if(netIntfo==null){
            //如果没有网络 显示不正常
        }else{
            //如果没有网络 显示不正常
        }
    }
    public static boolean isNetworkAvailable(Context context) {

    }
}
