package cn.ssdut.lst.easyreader.customtabs;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;

/**
 * Created by lisongting on 2017/4/24.
 */

public class CustomTabActivityHelper implements ServiceConnectionCallback {

    private CustomTabsSession mCustomTabsSession;
    private CustomTabsClient mClient;
    private CustomTabsServiceConnection mConnection;
    private ConnectionCallback mConnectionCallback;

    public interface ConnectionCallback{

        void onCustomTabsConnected();

        void onCustomTabsDisconnected();
    }

    public static void openCustomTab(Activity activity, CustomTabsIntent intent,
                                     Uri uri, CustomTabFallback fallback) {
        String packageName = CustomTabsHelper
    }

    public interface CustomTabFallback{

        void openUri(Activity activity, Uri uri);

    }
    @Override
    public void onServiceConnected(CustomTabsClient client) {
        mClient = client;
        mClient.warmup(0L);
        if (mConnectionCallback != null) {
            mConnectionCallback.onCustomTabsConnected();
        }
    }

    @Override
    public void onServiceDisconnected() {
        mClient = null;
        mCustomTabsSession = null;
        if (mConnectionCallback != null) {
            mConnectionCallback.onCustomTabsDisconnected();
        }
    }
}
