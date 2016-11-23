package cn.ssdut.lst.player;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/14.
 */
public class MusicService extends Service {
    public static final int STATUS_NONE=11;//没有播放
    public static final int STATUS_PLAY=22;//播放
    public static final int STATUS_PAUSE=33;//暂停
    public static final int CONTROL_NEXT=111;
    public static final int CONTROL_PREV=222;
    public static final int CONTROL_PLAYorPAUSE=333;
    public static final int CONTROL_STOP=444;
    MyReceiver serviceReceiver;
    AssetManager am;
    String[] music = new String[]{"Free Loop.mp3","Little Flower.mp3","you are beautiful.mp3"};
    MediaPlayer mplayer;
    int status = STATUS_NONE;//播放状态
    int currentSong =0;
    private MyBinder myBinder = new MyBinder();
    public class MyBinder extends Binder {
        public MediaPlayer getPlayer(){
            return mplayer;
        }
    }
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public void onCreate(){
        super.onCreate();
        am = getAssets();//获得AssetManager对象
        serviceReceiver = new MyReceiver();
        //给这个Reicever进行注册
        IntentFilter filter = new IntentFilter(MainActivity.CTL_ACTION);
        registerReceiver(serviceReceiver, filter);
        mplayer = new MediaPlayer();
        //为MediaPlayer播放完成绑定事件监听器
        mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer mp) {
                currentSong++;
                if(currentSong>=3){
                    currentSong=0;
                }
                //发送广播通知Activity改变界面
                Intent sendIntent = new Intent(MainActivity.UPDATE_ACTION);
                sendIntent.putExtra("currentSong", currentSong);
                //因为这一步只用告诉MainActivity我要播放下一首歌就行了，播放状态没有变，只是切换了一首歌而已，不用更改主界面的图标
                //所以不用status发送给MainActivity
                //sendIntent.putExtra("playStatus",status);
                sendBroadcast(sendIntent);
                prepareAndPlay(music[currentSong]);
            }
        });

    }
    public class MyReceiver extends BroadcastReceiver{
        public void onReceive(Context context,Intent intent){
            int control = intent.getIntExtra("control",-1);
            switch(control){
                case CONTROL_NEXT:
                    if(currentSong<2){
                        currentSong++;
                    }else if(currentSong==2){
                        currentSong=0;
                    }
                    prepareAndPlay(music[currentSong]);
                    status=STATUS_PLAY;
                    break;
                case CONTROL_PLAYorPAUSE:
                    //如果原来在播放状态
                    if(status==STATUS_PLAY){
                        mplayer.pause();
                        status = STATUS_PAUSE;
                    }else if(status==STATUS_NONE){//如果原来没有处于播放状态
                        prepareAndPlay(music[currentSong]);
                        status = STATUS_PLAY;
                    }else if(status==STATUS_PAUSE){
                        mplayer.start();//这个函数可以继续播放暂停的音乐
                        status = STATUS_PLAY;
                    }
                    break;
                case CONTROL_PREV:
                    if(currentSong>0){
                        currentSong--;
                    }else if(currentSong==0){
                        currentSong=2;
                    }
                    prepareAndPlay(music[currentSong]);
                    status=STATUS_PLAY;
                    break;
                case CONTROL_STOP:
                    if(status==STATUS_PAUSE||status==STATUS_PLAY){//如果原来处于播放或暂停状态，则停止播放
                        mplayer.stop();
                        status=STATUS_NONE;
                    }
                    break;
            }
            //发送广播通知MainActivity更改界面
            Intent intent2 = new Intent(MainActivity.UPDATE_ACTION);
            intent2.putExtra("playStatus", status);
            intent2.putExtra("currentSong", currentSong);
            sendBroadcast(intent2);
        }
    }

    public void prepareAndPlay(String music) {
        try{
            //打开指定的音乐文件
            AssetFileDescriptor afd = am.openFd(music);//
            mplayer.reset();
            //使用MediaPlayer加载指定的声音文件
            mplayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mplayer.prepare();//准备声音
            mplayer.start();//播放
        }catch(IOException e){
         e.printStackTrace();
        }
    }

}
