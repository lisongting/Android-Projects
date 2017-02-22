package cn.ssdut.lst.musicplayer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import java.util.Timer;
import java.util.TimerTask;

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
    public final String UPDATE_UI = "cn.lst.action.update_ui";
    public final String CTRL_PLAYER = "cn.lst.action.ctrl.player";
    private ServiceReceiver serviceReceiver;
    public MediaPlayer player;
    private AssetManager assetManager;
    private final String[] musics =
            new String[]{"Devize - Little Flower.mp3","Julie - Tomorrow.mp3"
                    ,"Michael Buble - Home.mp3","Virtual Riot - Energy Drink.mp3"};
    int currentStatus = STATUS_NONE;//初始状态
    int currentSong = 0;
    private int songDuration,currentPos =0;

    public void onCreate(){
        assetManager = getAssets();
        IntentFilter filter = new IntentFilter(CTRL_PLAYER);
        serviceReceiver = new ServiceReceiver();
        //注册BroadcastReceiver
        registerReceiver(serviceReceiver, filter);
        player = new MediaPlayer();
        //给MediaPlayer的播放完成事件注册监听器
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer mp) {
                currentSong++;
                if(currentSong>=4){
                    currentSong =0;
                }
                Intent t = new Intent(UPDATE_UI);
                //告诉MainActivity，player正在播放哪首歌
                t.putExtra("currentSong",currentSong);
                sendBroadcast(t);
                prepareAndPlay(musics[currentSong]);
            }
        });
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //启动一个定时任务，用来往MainActivity中发送进度信息并更新进度条
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                //如果歌曲在播放，则更新
                if (currentStatus == STATUS_PLAY) {
                    int pos = player.getCurrentPosition() / 1000;
                    Intent tmp = new Intent(UPDATE_UI);
                    tmp.putExtra("pos", pos);
                    //发送广播给MainActivity，让进度条更新
                    sendBroadcast(tmp);
                }
            }
        },0,500);
        return START_NOT_STICKY;
    }
    //播放音乐
    private void prepareAndPlay(String music){
        try {
            AssetFileDescriptor afd = assetManager.openFd(music);
            player.reset();
            player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int action = intent.getIntExtra("control", -1);
            int progressChanged = intent.getIntExtra("progressChanged", -1);
            switch(action){
                case CONTROL_REWIND:
                    //判断是否在播放
                    //在播放的话就进度减10s
                    if(currentStatus==STATUS_PLAY){
                        currentPos = player.getCurrentPosition();
                        if(currentPos>=10*1000){
                            player.seekTo(currentPos - 1000 * 10);
                        }else{
                            currentPos=0;
                            player.seekTo(currentPos);
                        }
                        currentStatus = STATUS_PLAY;
                    }
                    break;
                case CONTROL_PREV:
                    if(currentSong>=1){
                        currentSong--;
                    }else{
                        currentSong = 3;
                    }
                    prepareAndPlay(musics[currentSong]);
                    currentStatus = STATUS_PLAY;
                    break;
                case CONTROL_PLAYorPAUSE:
                    //判断是否在播放
                    if (currentStatus == STATUS_NONE) {
                        prepareAndPlay(musics[currentSong]);
                        currentStatus = STATUS_PLAY;
                    } else if (currentStatus == STATUS_PAUSE) {
                        player.start();
                        currentStatus = STATUS_PLAY;
                    } else if (currentStatus == STATUS_PLAY) {
                        player.pause();
                        currentStatus = STATUS_PAUSE;
                    }
                    break;
                case CONTROL_STOP:
                    if (player.isPlaying()) {
                        player.stop();
                        currentStatus = STATUS_NONE;
                    }
                    break;
                case CONTROL_NEXT:
                    if(currentSong<3){
                        currentSong = currentSong + 1;
                    }else {
                        currentSong=0;
                    }
                    prepareAndPlay(musics[currentSong]);
                    currentStatus = STATUS_PLAY;
                    break;
                case CONTROL_SPEED:
                    if (currentStatus == STATUS_PLAY) {
                        currentPos = player.getCurrentPosition();
                        if (songDuration - currentPos > 10 * 1000) {
                            currentPos =currentPos + 10 * 1000;
                        }
                        player.seekTo(currentPos);
                        currentStatus = STATUS_PLAY;
                    }
                    break;
                default:
                    break;
            }
            if (progressChanged > 0) {
                player.seekTo(progressChanged*1000);
            }
            //发送广播通知MainActivity改变界面
            Intent intent2 = new Intent(UPDATE_UI);
            intent2.putExtra("currentSong", currentSong);
            intent2.putExtra("currentStatus", currentStatus);
            songDuration = player.getDuration();
            intent2.putExtra("duration", songDuration/1000);
            sendBroadcast(intent2);
        }
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy(){
        player.stop();
        player.release();
    }
}
