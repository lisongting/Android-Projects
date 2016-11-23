package cn.ssdut.lst.musicbox;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/6.
 */
public class MusicService extends Service {
    MyReceiver serviceReceiver;
    AssetManager am;
    String[] musics = new String[]{"wish.mp3","promise.mp3","beautiful.mp3"};
    MediaPlayer mPlayer;
    int status = 0x11;
    int current =0;
    public IBinder onBind(Intent intent){
        return null;
    }
    public void onCreate(){
        super.onCreate();
        am = getAssets();
        serviceReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.CTL_ACTION);
        registerReceiver(serviceReceiver,filter);
        mPlayer  = new MediaPlayer();
        //为MediaPlayer播放完成事件绑定监听器
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer mp){
                current++;
                if(current>=3)
                    current =0;
                //发送广播通知Activity更改文本框
                Intent sendIntent = new Intent(MainActivity.UPDATE_ACTION);
                sendIntent.putExtra("current",current);
                sendBroadcast(sendIntent);
                prepareAndPlay(musics[current]);
            }
        });
    }
    public class MyReceiver extends BroadcastReceiver {
        public void onReceive(final Context context, Intent intent){
            int control = intent.getIntExtra("control",-1);
            switch(control){
                //播放或暂停
                case 1:
                    //原来处于没有播放状态
                    if(status ==0x11){
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }else if(status==0x12){//原来处于播放状态
                        mPlayer.pause();
                        status = 0x13;
                    }else if(status==0x13){
                        mPlayer.start();
                        status = 0x12;
                    }
                    break;
                //停止声音
                case 2:
                    //如果原来正在播放或暂停
                    if(status==0x12||status==0x13){
                        mPlayer.stop();
                        status = 0x11;
                    }
                    break;
                case 3:
                    if(current>0&&current<3){
                        current--;
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }else{
                        current =2;
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                 break;
                case 4:
                    if(current>=0&&current<2){
                        current++;
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }else{
                        current =0;
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    break;

            }
            //广播通知Activity更改图标，文本框
            Intent sendIntent = new Intent(MainActivity.UPDATE_ACTION);
            sendIntent.putExtra("update",status);
            sendIntent.putExtra("current",current);
            //发送广播，将被MainActivity中的BroadcastReceiver接收到
            sendBroadcast(sendIntent);
        }
    }
    private void prepareAndPlay(String music){
        try{
            AssetFileDescriptor afd = am.openFd(music);
            mPlayer.reset();
            //使用MediaPlayer加载指定声音
            mPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mPlayer.prepare();
            mPlayer.start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
