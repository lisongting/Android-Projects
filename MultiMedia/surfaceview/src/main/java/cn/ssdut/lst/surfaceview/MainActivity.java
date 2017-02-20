package cn.ssdut.lst.surfaceview;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
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
        //给SurfaceView添加一个Callback对象，用来实时监听SurfaceView的状态
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
                        //start()可从头播放，也可继续播放
                        player.start();
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
        player.setDataSource("/mnt/sdcard/data/video.mp4");

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
        //可以根据具体需求在下面回调方法中写出更具体的处理代码
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i("tag", "surface Created()");
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i("tag", "surface Changed()");
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i("tag", "surface Destroyed()");
        }
    }
    public void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
        }
        super.onDestroy();
    }
}
