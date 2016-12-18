package cn.ssdut.lst.pinball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int tableWidth;
    private int tableHeight;
    private int racketY;
    //球拍的宽度和高度
    private final int RACKET_WIDTH = 90;
    private final int RACKET_HEIGHT= 30;
    private final int BALL_SIZE = 16;
    private int ySpeed = 18;
    Random rand = new Random();
    //返回一个-0.5到0.5的数，用于控制小球的运行方向
    private double xyRate = rand.nextDouble()-0.5;
    private int xSpeed = (int)(ySpeed *xyRate *2);//小球的横向运行速度，不知道为什么这样设置
    //小球的横纵坐标
    private int ballX = rand.nextInt(300) +20;//300以内的随机数加20
    private int ballY = rand.nextInt(10)+20;
    private int racketX = rand.nextInt(200);//球拍的水平位置
    private boolean isLose = false;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);一行没用的代码，AppcompatActivity始终会显示title栏
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final GameView gameView = new GameView(this);
        setContentView(gameView);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //获取屏幕的宽和高
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        Toast.makeText(MainActivity.this, "tableWidth:"+tableWidth+";tableHeight"+tableHeight, Toast.LENGTH_SHORT).show();
        racketY = tableHeight-200;
        handler = new Handler(){
            public void handleMessage(Message msg){
                if (msg.what == 123) {
                    gameView.invalidate();
                }
            }
        };
        /*
        gameView.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View source, int keyCode, KeyEvent event){
                switch (event.getKeyCode()) {
                    //控制挡板左移
                    case KeyEvent.KEYCODE_A:
                        if (racketX > 0)
                            racketX-=20;
                        break;
                    case KeyEvent.KEYCODE_D:
                        if(racketX<(tableWidth-RACKET_WIDTH))
                            racketX+=20;
                        break;
                }
                gameView.invalidate();
                return true;
            }

        });*/

        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                if (ballX <= 0 || ballX >= tableWidth - BALL_SIZE) {
                    xSpeed = -xSpeed;
                }
                //如果小球高度超过了球拍的高度，且横向不在球拍内，游戏结束
                if (ballY >= racketY - BALL_SIZE && (ballX < racketX || ballX > racketX + RACKET_WIDTH)) {
                    timer.cancel();
                    isLose=true;
                } else if (ballY <= 0 || (ballY >= racketY - BALL_SIZE && ballX <= racketX + RACKET_WIDTH)) {
                    //如果小球碰到屏幕上方，或者触碰到球拍
                    ySpeed =-ySpeed;
                }
                //小球坐标增加
                ballY += ySpeed;
                ballX += xSpeed;
                handler.sendEmptyMessage(123);//通知系统重绘组件
            }
        },0,50);//每0.05秒触发一次判断
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
                paint.setColor(Color.rgb(0, 0, 255));
                //drawRect(left,top,right,bottom)
                canvas.drawRect(racketX, racketY, racketX + RACKET_WIDTH, racketY + RACKET_HEIGHT,paint);
            }
        }

        public boolean onTouchEvent(MotionEvent event) {

            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                float floatX = event.getX();
                if((floatX<tableWidth/2)&&racketX>=0){
                    racketX-=40;
                } else if (floatX > tableWidth / 2 && (racketX < (tableWidth - RACKET_WIDTH))) {
                    racketX+=40;
                }
            }
            handler.sendEmptyMessage(123);
            return true;
        }
    }
}
