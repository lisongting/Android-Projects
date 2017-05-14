package cn.ssdut.lst.easyreader.customtabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import cn.ssdut.lst.easyreader.innerbrowser.InnerBrowserActivity;

/**
 * Created by lisongting on 2017/4/24.
 */

public class CustomFallback implements CustomTabActivityHelper.CustomTabFallback{

    @Override
    public void openUri(Activity activity, Uri uri) {
        activity.startActivity(new Intent(activity, InnerBrowserActivity.class).putExtra("url", uri.toString()));
    }

}
