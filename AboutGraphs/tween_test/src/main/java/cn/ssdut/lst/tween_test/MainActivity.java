package cn.ssdut.lst.tween_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView flower = (ImageView) findViewById(R.id.flower);
        //加载两个个动画资源
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        final Animation reverse = AnimationUtils.loadAnimation(this, R.anim.reverse);
        //设置动画结束后的保留状态
        anim.setFillAfter(true);
        reverse.setFillAfter(true);
        Button bn = (Button) findViewById(R.id.bn);
        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                if (msg.what == 123) {
                    flower.startAnimation(reverse);
                }
            }
        };
        bn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                flower.startAnimation(anim);
                //设置2秒钟后启动第二个动画，将动画还原
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        handler.sendEmptyMessage(123);
                    }
                }, 2000);
            }
        });
    }
}
