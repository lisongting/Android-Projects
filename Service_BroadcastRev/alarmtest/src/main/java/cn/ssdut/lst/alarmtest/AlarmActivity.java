package cn.ssdut.lst.alarmtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/11/6.
 */
public class AlarmActivity extends Activity {
    MediaPlayer alarmMusic;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        alarmMusic = MediaPlayer.create(this,R.raw.music);
        alarmMusic.setLooping(true);
        alarmMusic.start();
        new AlertDialog.Builder(AlarmActivity.this)
                .setTitle("闹钟")
                .setMessage("闹钟响了，gogogo!")
                .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        alarmMusic.stop();
                        AlarmActivity.this.finish();
                    }
                }).show();
    }

}
