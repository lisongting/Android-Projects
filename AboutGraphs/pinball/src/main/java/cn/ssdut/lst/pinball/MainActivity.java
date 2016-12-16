package cn.ssdut.lst.pinball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int tableWidth;
    private int tableHeight;
    private int racketY;
    //球拍的宽度和高度
    private final int RACKET_WIDTH = 90;
    private final int RACKET_HEIGHT= 30;
    private final int BALL_SIZE = 16;
    private int ySpeed = 15;
    Random rand = new Random();
    //返回一个-0.5到0.5的数，用于控制小球的运行方向
    private double xyRate = rand.nextDouble()-0.5;
    private int xSpeed = (int)(ySpeed *xyRate *2);//小球的横向运行速度，不知道为什么这样设置
    //小球的横纵坐标
    private int ballX = rand.nextInt(200) +20;//两百以内的随机数加20
    private int ballY = rand.nextInt(10)+20;
    private int racketX = rand.nextInt(200);//球拍的水平位置
    private boolean isLose = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class GameView extends View {
        Paint paint = new Paint();

        public GameView(Context context) {
            super(context);
            setFocusable(true);//可以接受焦点
        }

        public void onDraw(Canvas canvas) {
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            if(isLose){
                paint.setColor(Color.RED);
                paint.setTextSize(40);
                canvas.drawText("游戏已结束", tableWidth / 2 - 100, 200, paint);
            }else{
                //设置颜色，并绘制小球
                paint.setColor(Color.rgb(255,0,0));//红色
                canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);
                //绘制球拍
                paint.setColor(Color.rgb(80, 80, 200));
                canvas.drawRect(racketX, racketY, racketX + RACKET_WIDTH, racketY + RACKET_HEIGHT,paint);
            }
        }
    }
}
