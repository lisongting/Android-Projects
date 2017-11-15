package com.paperfish.streammediatest;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by lisongting on 2017/10/26.
 */
public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button start;
    private Button stop,bt_rgb,bt_depth;
    private EditText editText;
    private MediaController mediaController;

    private static final String RTMP_RGB = "rtmp://192.168.0.107/rgb ";
    private static final String RTMP_DEPTH = "rtmp://192.168.0.107/depth ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.bt_start);
        stop = (Button) findViewById(R.id.bt_stop);
        editText = (EditText) findViewById(R.id.url);
        videoView = (VideoView) findViewById(R.id.vitamio_videoview);
        bt_rgb = (Button) findViewById(R.id.bt_rgb);
        bt_depth = (Button) findViewById(R.id.bt_depth);
        if (!Vitamio.initialize(this)) {
            log("Vitamio初始化失败");
            return;
        }


        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_MEDIUM);
        mediaController = new MediaController(this);
        videoView.setBufferSize(10240);
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "已就绪", Toast.LENGTH_SHORT).show();
                mp.setPlaybackSpeed(1.0F);
            }
        });

        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
            }
        });

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        log("开始缓冲");
                        mp.pause();
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        log("缓冲结束");
                        mp.start();
                        break;
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        log("当前网速：" + extra);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.stopPlayback();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getEditableText().toString();
                videoView.setVideoURI(Uri.parse(s));
                videoView.start();
            }
        });

        bt_rgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoURI(Uri.parse(RTMP_RGB));
                videoView.start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void log(String s) {
        Log.i("tag", s);
    }
}
