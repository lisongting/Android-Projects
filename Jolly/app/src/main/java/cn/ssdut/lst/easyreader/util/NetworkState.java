package cn.ssdut.lst.easyreader.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/3/28.
 */

public class NetworkState {
    //检查是否连接网络
    public static boolean networkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
//            Log.i("tag", "info:" + info.getTypeName() + "," + info.getType());
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    public static boolean wifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info.getType() != ConnectivityManager.TYPE_WIFI) {
                return info.isAvailable();
            }
        }
        return false;
    }

    public static boolean mobileDataConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info.getType() != ConnectivityManager.TYPE_MOBILE) {
                return info.isAvailable();
            }
        }
        return false;
    }
}
