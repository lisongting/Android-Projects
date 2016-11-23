package cn.ssdut.lst.soundpool;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bomb,shot,arrow;
    SoundPool soundPool;
    Map<Integer, Integer> soundMap = new HashMap<>();

    @Override
    @TargetApi(21)//因为AudioAttributes.builder只能在高于21的版本上用，所以加一个Annotation
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bomb =(Button) findViewById(R.id.bomb);
        bomb.setOnClickListener(this);
        shot = (Button)findViewById(R.id.shot);
        shot.setOnClickListener(this);
        arrow = (Button) findViewById(R.id.arrow);
        arrow.setOnClickListener(this);
        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)//设置音效使用场景
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)//设置音效的类型
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(10)//最多可容纳10个音频流
                .build();
        //load方法加载指定的音频文件，并返回加载的音频Id
        //使用HashMap管理这些音频流
        soundMap.put(1, soundPool.load(this,R.raw.bomb,1));//1是priority，这个参数暂时没有作用，使用1可以保持以后的兼容性
        soundMap.put(2,soundPool.load(this,R.raw.shot,1));
        soundMap.put(3,soundPool.load(this,R.raw.arrow,1));

    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bomb:
                soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
                break;
            case R.id.arrow:
                soundPool.play(soundMap.get(2), 1, 1, 0, 0, 1);
                break;
            case R.id.shot:
                soundPool.play(soundMap.get(3), 1, 1, 0, 0, 1);
                break;
        }
    }
}
