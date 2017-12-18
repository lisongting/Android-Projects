package com.paperfish.streammediatest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.IMediaController;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

/**
 * 使用七牛PLDroidPlayer播放rtmp流媒体
 * 使用PLDroidPlayer，在我推流端正确的时候，播放的视频画质非常差，
 * 有的时候一帧都显示不出来，还是使用ijkPlayer能够正常播放
 * Created by lisongting on 2017/11/8.
 */

public class PLMediaActivity extends AppCompatActivity {


    private Button start,stop,bt_rgb,bt_depth;
    private EditText editText;
    private PLVideoTextureView textureView;
    IMediaController mediaController;

    private static final String RTMP_RGB = "rtmp://192.168.0.118/rgb ";
//    private static final String RTMP_RGB = "rtmp://10.0.0.24/rgb ";

    private static final String RTMP_DEPTH = "rtmp://192.168.0.107/depth ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl);

        start = (Button) findViewById(R.id.bt_start);
        stop = (Button) findViewById(R.id.bt_stop);
        editText = (EditText) findViewById(R.id.url);
        bt_rgb = (Button) findViewById(R.id.bt_rgb);
        bt_depth = (Button) findViewById(R.id.bt_depth);
        textureView = (PLVideoTextureView) findViewById(R.id.plTextureView);


    }

    @Override
    protected void onResume() {
        super.onResume();

        textureView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_FIT_PARENT);

        textureView.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                log(" onInfo --  what:"+i+"  extra:"+i1);
                return false;
            }
        });
        textureView.setOnBufferingUpdateListener(new PLMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int i) {
                log("buffer:"+i);
            }
        });
        AVOptions opts = new AVOptions();

        opts.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION,1000);
        opts.setInteger(AVOptions.KEY_LIVE_STREAMING,1);
//        opts.setInteger(AVOptions.KEY_MEDIACODEC,1);
        textureView.setAVOptions(opts);

        textureView.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                Log.i("tag", "onError code:" + i);
                return false;
            }
        });
        textureView.setOnVideoFrameListener(new PLMediaPlayer.OnVideoFrameListener() {
            @Override
            public void onVideoFrameAvailable(byte[] bytes, int i, int i1, int i2, int i3, long l) {

                log("onVideoFrame:" + i + " , " + i1 + " , " +i2 + " , " + i3 + " , " + l);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getEditableText().toString();
                textureView.setVideoPath(s);
                textureView.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("isPlaying :" + textureView.isPlaying());
                if (textureView.isPlaying()) {
                    textureView.pause();
                    textureView.stopPlayback();
                }
            }
        });

        bt_rgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textureView.setVideoPath(RTMP_RGB);
                textureView.start();
            }
        });



    }

    private void log(String s) {
        Log.i("tag", s);
    }
}
