package cn.ssdut.lst.surfaceview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

/**
 * 用SurfaceView和MediaPlayer来实现的视频播放器
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SurfaceView surfaceView;
    Button play,pause,stop;
    MediaPlayer player;
    int position;//记录视频播放的位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(this);
        pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(this);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(this);
        player = new MediaPlayer();
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        //设置播放时打开屏幕
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(new SurfaceListener());
    }

    public void onClick(View source) {
        try {
            switch (source.getId()) {
                case R.id.play:
                    play();
                    break;
                case R.id.pause:
                    if (player.isPlaying()) {
                        player.pause();
                    }else{
                        player.start();//播放或者继续播放
                    }
                    break;
                case R.id.stop:
                    if (player.isPlaying()) {
                        player.stop();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play()throws IOException {
        player.reset();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource("/mnt/sdcard/DCIM/Video/test.mp4");
        player.setDisplay(surfaceView.getHolder());
        player.prepare();
        WindowManager wManager = getWindowManager();//获取窗口管理器
        DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕大小,displayMetrics包含着这个ViewHolder的宽高大小和密度等属性
        wManager.getDefaultDisplay().getMetrics(metrics);
        //保持视频横纵比缩放到占满整个屏幕
        surfaceView.setLayoutParams(new LinearLayout.LayoutParams
                (metrics.widthPixels, player.getVideoHeight() * metrics.widthPixels / player.getVideoWidth()));
        player.start();
    }
    //定义一个监听器，实现SurfaceHolder.Callback接口
    private class SurfaceListener implements SurfaceHolder.Callback{
        //写出下面方法的具体实现
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (position > 0) {
                try {
                    play();
                    player.seekTo(position);
                    position=0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
    //当其他Activity打开时，暂停播放
    public void onPause() {
        if (player.isPlaying()) {
            //保存当前播放位置
            position=player.getCurrentPosition();
            player.stop();
        }
        super.onPause();
    }

    public void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
        }
        super.onDestroy();
    }
}
