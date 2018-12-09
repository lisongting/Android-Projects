package com.lst.calculator;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class Server extends Service {

    public static final int SHAPE_RECTANGLE =0;
    public static final int SHAPE_SQUARE =1;
    public static final int SHAPE_CIRCLE =2;
    public static final int SHAPE_TRIANGLE =3;

    private Messenger clientMessenger;
    private  Handler serviceHandler ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        serviceHandler = new Handler(){
            public void handleMessage(Message msg) {
                clientMessenger = msg.replyTo;
                float result = 0F;
                Float length = msg.getData().getFloat("l",0);
                Float width = msg.getData().getFloat("w",0);
                switch (msg.what) {
                    case SHAPE_RECTANGLE:
                        result = length * width;
                        break;
                    case SHAPE_CIRCLE:
                        //形状为圆形时传入length为半径
                        result = (float) (Math.PI * length * length);
                        break;
                    case SHAPE_SQUARE:
                        result = length * length;
                        break;
                    case SHAPE_TRIANGLE:
                        result = (float) (0.5 * length * width);
                        break;
                    default:
                        break;
                }
                Message message = Message.obtain();
                Bundle b = new Bundle();
                Log.i("server", "handleMessage: lenght" + length + ",height:" + width +" , what:"+msg.what);
                b.putFloat("result",result);
                Log.i("server", "result:" + result);
                message.setData(b);
                try {
                    clientMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };

        return new Messenger(serviceHandler).getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
