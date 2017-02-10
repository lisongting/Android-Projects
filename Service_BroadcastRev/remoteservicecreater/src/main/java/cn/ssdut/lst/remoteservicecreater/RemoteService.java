package cn.ssdut.lst.remoteservicecreater;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;


public class RemoteService extends Service {
    private IMyAidlInterface.Stub
            binder = new IMyAidlInterface.Stub(){
        public String getMessage() throws RemoteException {
            return "远程服务调用成功！";
        }
    };
    public void onCreate() {
        Log.i("tag","RemoteService所在的进程ID是:"+ Process.myPid());
        Log.i("tag","RemoteService----onCreate()");
        super.onCreate();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag","RemoteService----onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }
    public void onDestroy() {
        Log.i("tag","RemoteService----onDestroy()");
        super.onDestroy();
    }
    public IBinder onBind(Intent intent) {
        Log.i("tag","RemoteService----onBind()");
        return binder;
    }
    public boolean onUnbind(Intent intent) {
        Log.i("tag","RemoteService----onUnbind()");
        return super.onUnbind(intent);
    }
}
