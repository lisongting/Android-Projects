package cn.ssdut.lst.musicplayer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Administrator on 2017/2/21.
 */

public class MusicService extends Service {
    //status用来描述Service中的播放器状态
    public final int STATUS_NONE = 11;//没有播放
    public final int STATUS_PLAY = 22;//正在播放
    public final int STATUS_PAUSE =33;//暂停状态
    //control用来描述
    public final int CONTROL_PREV = 111;//上一首
    public final int CONTROL_NEXT = 222;
    public final int CONTROL_PLAYorPAUSE = 333;
    public final int CONTROL_STOP = 444;
    public final int CONTROL_SPEED = 555;//快进
    public final int CONTROL_REWIND =666;//快退
    private MyReceiver myReceiver;
    private MediaPlayer player;
    private AssetManager assetManager;
    private MyBinder binder = new MyBinder();
    private final String[] musics =
            new String[]{"Devize - Little Flower.mp3","Julie - Tomorrow.mp3"
                    ,"Michael Buble - Home.mp3","Virtual Riot - Energy Drink.mp3"};
    int currentStatus = STATUS_NONE;//初始状态
    int currentSong = 0;
    public void onCreate(){

    }

    public class MyBinder extends Binder {
        public MediaPlayer getPlayer(){
            return player;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public IBinder onBind(Intent intent) {
        return binder;
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
