package cn.ssdut.lst.soundpooltest;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button b1,b2,b3,b4;
    SoundPool soundPool;
    int sounds[] = new int[4];
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.sound1);        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.sound2);        b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.sound3);        b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.sound4);        b4.setOnClickListener(this);
        //定义一个音频属性对象
        AudioAttributes attr = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        //使用SoundPool.Builder来生成SoundPool对象
        soundPool = new SoundPool.Builder()
                //设置音效池的属性
                .setAudioAttributes(attr)
                //设置最大可容纳的音频数
                .setMaxStreams(10)
                .build();
        //load方法加载指定音频，并返回加载的音频id
        sounds[0] = soundPool.load(this, R.raw.sound1, 1);
        sounds[1] = soundPool.load(this, R.raw.sound2, 1);
        sounds[2] = soundPool.load(this, R.raw.sound3, 1);
        sounds[3] = soundPool.load(this, R.raw.sound4, 1);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sound1:
                soundPool.play(sounds[0],1,1,0,0,1);
                break;
            case R.id.sound2:
                soundPool.play(sounds[1],1,1,0,0,1);
                break;
            case R.id.sound3:
                soundPool.play(sounds[2],1,1,0,0,1);
                break;
            case R.id.sound4:
                soundPool.play(sounds[3],1,1,0,0,1);
                break;
        }
    }
}
