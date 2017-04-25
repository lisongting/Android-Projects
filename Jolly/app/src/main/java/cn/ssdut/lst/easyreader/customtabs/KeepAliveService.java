package cn.ssdut.lst.easyreader.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by lisongting on 2017/4/24.
 */

public class KeepAliveService extends Service {

    private static final Binder sBinder = new Binder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }
}
