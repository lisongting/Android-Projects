package cn.ssdut.lst.easyreader.customtabs;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;

import java.util.List;

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

    //如果可以的话，打开自定义的tab，否则就使用webView来打开
    public static void openCustomTab(Activity activity,
                                     CustomTabsIntent customTabsIntent,
                                     Uri uri,
                                     CustomTabFallback fallback) {
        String packageName = CustomTabsHelper.getsPackageNameToUse(activity);

        //如果能够找到一个包名，则说明没有浏览器能够支持Chrome 的Tabs,然后去使用webview
        if (packageName == null) {
            if (fallback != null) {
                fallback.openUri(activity, uri);
            }
        } else {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }
    }

    //将Activity与自定义Tabs service取消绑定
    public void unbindCustomTabsService(Activity activity) {
        if (mConnection == null) {
            return ;
        }
        activity.unbindService(mConnection);
        mClient = null;
        mCustomTabsSession = null;
        mConnection = null;
    }

    public CustomTabsSession getSession() {
        if (mClient == null) {
            mCustomTabsSession = null;
        } else if (mCustomTabsSession == null) {
            mCustomTabsSession = mClient.newSession(null);
        }
        return mCustomTabsSession;
    }

    public void bindCustomTabsService(Activity activity) {
        if (mClient != null) {
            return ;
        }
        String packageName = CustomTabsHelper.getsPackageNameToUse(activity);
        if (packageName == null) {
            return ;
        }
        mConnection = new ServiceConnection(this);
        CustomTabsClient.bindCustomTabsService(activity, packageName, mConnection);
    }

    public boolean mayLaunchUrl(Uri uri, Bundle bundle, List<Bundle> otherLikelyBundles) {
        if (mClient == null) {
            return false;
        }
        CustomTabsSession session = getSession();
        if (session == null) {
            return false;
        }
        return session.mayLaunchUrl(uri,bundle, otherLikelyBundles);
    }

}
