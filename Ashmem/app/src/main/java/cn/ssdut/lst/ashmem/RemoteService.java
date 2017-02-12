package cn.ssdut.lst.ashmem;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
/**
 * Created by Administrator on 2017/2/12.
 */

public class RemoteService extends Service {

    private MemoryStub memoryService =null;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("tag", "Memory Service----onBind()");
        return memoryService;
    }

    @Override
    public void onCreate() {
        Log.i("tag", "Memory Service----onCreate()");
        memoryService = new MemoryStub();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i("tag", "Memory Service----onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("tag", "Memory Service----onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag", "Memory Service----onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }
}
