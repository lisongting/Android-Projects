package com.paperfish.streammediatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 测试使用IJKPlayer播放流媒体
 * Created by lisongting on 2017/11/29.
 */

public class PlayerActivity extends AppCompatActivity {
    private Button start,stop,bt_rgb;
    private EditText editText;
    private IjkMediaPlayer ijkMediaPlayer;
    private SurfaceView surfaceView;

    private static final String RTMP_RGB = "rtmp://192.168.0.118/rgb ";
//    private static final String RTMP_RGB = "rtmp://10.0.0.24/rgb ";

    private static final String RTMP_DEPTH = "rtmp://192.168.0.118/depth ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijkplayer);

        start = (Button) findViewById(R.id.bt_start);
        stop = (Button) findViewById(R.id.bt_stop);
        editText = (EditText) findViewById(R.id.url);
        bt_rgb = (Button) findViewById(R.id.bt_rgb);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);

        getSupportActionBar().setTitle("测试IJKPlayer");

        bt_rgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(RTMP_RGB);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = editText.getEditableText().toString();
                if (!path.isEmpty()) {
                    play(path);
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ijkMediaPlayer != null) {
                    ijkMediaPlayer.stop();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        createPlayer();
    }

    private void play(String path) {
        try {
            ijkMediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.setDisplay(surfaceView.getHolder());
        ijkMediaPlayer.prepareAsync();


    }

    private void createPlayer() {
        ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        ijkMediaPlayer.setOnNativeInvokeListener(new IjkMediaPlayer.OnNativeInvokeListener() {
            @Override
            public boolean onNativeInvoke(int i, Bundle bundle) {
                log("onNativeInvoke:" + i);
                return false;
            }
        });

        ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                log("onPrepared()");
                iMediaPlayer.start();
//                log("--------------------");
//                log("MediaInfo: " + iMediaPlayer.getMediaInfo());
//                log("DataSource: " + iMediaPlayer.getDataSource());
//                log("bitrate: "+ijkMediaPlayer.getBitRate());
//                log("currentPosition：" + ijkMediaPlayer.getCurrentPosition());
//                log("videoCachedBytes : " + ijkMediaPlayer.getVideoCachedBytes());
//                log("videoCachedPackets : " + ijkMediaPlayer.getVideoCachedPackets());
//                log("videoCachedDuration : " + ijkMediaPlayer.getVideoCachedDuration());
//                log("decodeFramePerSecond : " + ijkMediaPlayer.getVideoDecodeFramesPerSecond());
//                log("dropFrameRate : " + ijkMediaPlayer.getDropFrameRate());
//                log("--------------------");
            }
        });
        ijkMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                log("onInfo:" + i + " , " + i1);
                return false;
            }
        });
        ijkMediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
//                log("onBufferingUpdate:" + i);
                log("videoCachedBytes : " + ijkMediaPlayer.getVideoCachedBytes());
                log("videoCachedPackets : " + ijkMediaPlayer.getVideoCachedPackets());
                log("videoCachedDuration : " + ijkMediaPlayer.getVideoCachedDuration());
            }
        });
        ijkMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                log("onError:" + i + " , " + i1);
                return false;
            }
        });



    }



    private void log(String s) {
        Log.i("ijk", s);
    }
}
