package cn.ssdut.lst.remoteservicecreater;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/9.
 */

public class RemoteService extends Service {
    private IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub(){
        @Override
        public String getMessage() throws RemoteException {
            return "这是来自远程服务的消息";
        }
    };
    @Override
    public void onCreate() {
        Log.i("tag", "RemoteService所在线程Id是:" + Thread.currentThread().getId());
        Log.i("tag","RemoteService所在的进程ID是:"+ Process.myPid());
        Log.i("tag","RemoteService----onCreate()");
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag","RemoteService----onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Log.i("tag","RemoteService----onDestroy()");
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("tag","RemoteService----onBind()");
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("tag","RemoteService----onUnbind()");
        return super.onUnbind(intent);
    }
}
