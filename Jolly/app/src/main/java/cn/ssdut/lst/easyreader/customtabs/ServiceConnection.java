package cn.ssdut.lst.easyreader.customtabs;

import android.content.ComponentName;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;

import java.lang.ref.WeakReference;

/**
 * Created by lisongting on 2017/4/24.
 */

public class ServiceConnection extends CustomTabsServiceConnection {

    //强引用：通过new Xxx()方式生成的对象。绝对不会被垃圾回收器回收
    //软引用(soft)：如果内存不够用了，就回收只具有软引用的对象
    //弱引用（weak）：一旦垃圾回收器扫描到某个只具有弱引用的对象，不管内存够不够用，都回收它
    //虚引用（phantom）：虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收。

    private WeakReference<ServiceConnectionCallback> mConnectionCallback;

    public ServiceConnection(ServiceConnectionCallback callback) {
        mConnectionCallback = new WeakReference<>(callback);
    }

    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
        ServiceConnectionCallback connectionCallback = mConnectionCallback.get();
        if (connectionCallback != null) {
            connectionCallback.onServiceConnected(client);
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        ServiceConnectionCallback connectionCallback = mConnectionCallback.get();
        if (connectionCallback != null) {
            connectionCallback.onServiceDisconnected();
        }
    }
}
