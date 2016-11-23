package cn.ssdut.lst.audiomanager;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    AudioManager aManager;
    ToggleButton mute ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aManager = (AudioManager)getSystemService(Service.AUDIO_SERVICE);
        mute = (ToggleButton)findViewById(R.id.mute_button);
        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton source,boolean isChecked){
                aManager.setStreamMute(AudioManager.STREAM_MUSIC,isChecked);
            }
        });
    }
    public void play(View v){
        MediaPlayer mplayer = MediaPlayer.create(MainActivity.this,R.raw.music);
        mplayer.setLooping(true);
        mplayer.start();
    }

    public void up(View view) {
        aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,AudioManager.FLAG_SHOW_UI);
    }

    public void down(View view) {
        aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,AudioManager.FLAG_SHOW_UI);
    }


}
