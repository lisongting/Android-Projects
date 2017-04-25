package cn.ssdut.lst.easyreader.customtabs;

import android.support.customtabs.CustomTabsClient;

/**
 * Created by lisongting on 2017/4/24.
 */

public interface ServiceConnectionCallback {
    void onServiceConnected(CustomTabsClient client);

    void onServiceDisconnected();
}
