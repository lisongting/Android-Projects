package cn.ssdut.lst.frame_animation1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 测试逐帧动画(Frame,一帧一帧走)
 */
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private MyView myView;
    private AnimationDrawable anim2;
    private MediaPlayer bomb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = (Button) findViewById(R.id.play);
        Button stop = (Button) findViewById(R.id.stop);
        ImageView imageView = (ImageView) findViewById(R.id.anim);
        FrameLayout frame = (FrameLayout) findViewById(R.id.frameLayout);
        //获取AnimationDrawable的动画对象
        final AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        //如果是getDrawable的话，则会导致报错而播放不了
        //final AnimationDrawable anim = (AnimationDrawable) imageView.getDrawable();
        play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                anim.start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.stop();
            }
        });
        //为帧布局设置后背景
        bomb = MediaPlayer.create(this, R.raw.bomb);
        myView = new MyView(this);
        myView.setBackgroundResource(R.drawable.blast);
        myView.setVisibility(View.VISIBLE);//默认为显示
        anim2 = (AnimationDrawable) myView.getBackground();
        frame.addView(myView);
        frame.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View source, MotionEvent event) {
                //处理按下事件
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    anim.stop();//先停止动画播放
                    float x = event.getX();
                    float y = event.getY();
                    Toast.makeText(MainActivity.this, "坐标位置("+x+","+y+")", Toast.LENGTH_SHORT).show();
                    myView.setVisibility(View.VISIBLE);
                    anim2.start();
                    bomb.start();
                }
                return true;
            }
        });
    }
    class MyView extends View{
        public MyView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            try {
                Field field = AnimationDrawable.class.getDeclaredField("mCurFrame");
                field.setAccessible(true);
                //获取动画的当前帧
                int curFrame = field.getInt(anim2);
                if (curFrame == anim2.getNumberOfFrames() - 1) {
                    setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onDraw(canvas);
        }
    }
}
