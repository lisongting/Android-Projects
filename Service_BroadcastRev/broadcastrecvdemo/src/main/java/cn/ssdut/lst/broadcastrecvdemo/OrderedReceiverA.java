package cn.ssdut.lst.broadcastrecvdemo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
public class OrderedReceiverA extends BroadcastReceiver {
    private static int PASS = 1;
    private static int ABORT = 2;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle receive = intent.getExtras();
        int control = receive.getInt("control");
        String info = receive.getString("key");
        Log.i("tag", "OrderedReceiverA接收到广播:" + info);
        if(control==PASS){
            setResultExtras(receive);
        } else if (control == ABORT) {
            abortBroadcast();
        }
    }
}
