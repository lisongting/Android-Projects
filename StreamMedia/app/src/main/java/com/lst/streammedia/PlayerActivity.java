package com.lst.streammedia;

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
    private Button start,stop,bt_rgb,bt_depth,bt_start_custom_video;
    private EditText editText;
    private IjkMediaPlayer ijkMediaPlayer;
    private SurfaceView surfaceView;

    private boolean isPlaying = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijkplayer);

        start = (Button) findViewById(R.id.bt_start);
        stop = (Button) findViewById(R.id.bt_stop);
        editText = (EditText) findViewById(R.id.url);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);

        getSupportActionBar().setTitle("直播拉流测试");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = editText.getEditableText().toString();
                if (!path.isEmpty()&&!isPlaying) {
                    isPlaying = true;
                    play(path);
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ijkMediaPlayer != null&&isPlaying) {
                    isPlaying = false;
                    ijkMediaPlayer.stop();
                    ijkMediaPlayer.release();
                    ijkMediaPlayer.reset();
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
        createPlayer();
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

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 1024 * 16);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", 50000);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_frame", 0);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"packet-buffering",0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max_cached_duration", 3000);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "infbuf", 1);

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
                log("onBufferingUpdate:" + i);
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
