package com.test.administrator.layerdrabletest;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

/**
 * Created by Administrator on 2016/10/27.
 */
public class RawResActivity extends Activity {
    MediaPlayer p1=null;
    MediaPlayer p2;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rawres_layout);
        //直接根据声音的id来创建MediaPlayer
        p1 = MediaPlayer.create(this,R.raw.angela);
        try {
            AssetManager am = getAssets();
            //获取指定文件对应的AssetFileDescriptor
            AssetFileDescriptor afd = am.openFd("little.mp3");
            p2 = new MediaPlayer();
            p2.setDataSource(afd.getFileDescriptor());
            p2.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void playInRaw(View v){
        p1.start();
    }
    public void onStop(){
        super.onStop();
        p1.pause();
        p2.pause();
    }
    public void playInAsset(View view) {
        p2.start();
    }
}
