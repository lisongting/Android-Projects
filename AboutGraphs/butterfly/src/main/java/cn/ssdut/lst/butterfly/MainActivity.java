package cn.ssdut.lst.butterfly;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //记录蝴蝶ImageView当前的位置
    private float curX = 0;
    private float curY = 30;
    //记录蝴蝶的下一个位置坐标
    float nextX = 0;
    float nextY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.butterfly);
        final Handler handler = new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 123) {
                    if (nextX > 1080) {
                        curX = nextX = 0;
                    } else {
                        nextX += 8;
                    }
                    //纵向可随机上下飞
                    nextY = curY + (float) (Math.random()*10 - 5);
                    //设置蝴蝶的ImageView发生位移改变
                    TranslateAnimation anim = new TranslateAnimation(curX, nextX, curY, nextY);
                    curX = nextX;
                    curY = nextY;
                    anim.setDuration(200);
                    //开始位移动画
                    imageView.startAnimation(anim);
                }
            }
        };

        final AnimationDrawable butterfly = (AnimationDrawable)imageView.getBackground();
        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //播放蝴蝶振翅的动画
                butterfly.start();
                new Timer().schedule(new TimerTask(){
                    public void run() {
                        handler.sendEmptyMessage(123);//发送message让蝴蝶横向位移
                    }
                },0,200);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(MainActivity.this, "x坐标:"+event.getX()+"y坐标"+event.getY(), Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }
}
