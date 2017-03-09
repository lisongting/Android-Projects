package cn.ssdut.lst.desktopwidget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Created by Administrator on 2017/1/8.
 */

public class LiveWallPaper extends WallpaperService {
    private Bitmap heart;

    //实现WallPaperService必须实现的抽象方法
    public Engine onCreateEngine() {
        heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        //返回自定义的Engine
        return new MyEngine();
    }

    class MyEngine extends Engine {
        private boolean mVisible;
        private float mTouchX = -1;
        private float mTouchY = -1;
        //记录当前需要绘制的矩形数量
        private int count = 1;
        private int originX = 50;
        private int originY=50;
        private Paint mPaint = new Paint();
        Handler mHandler = new Handler();
        //定义一个周期执行的任务
        private final Runnable drawTarget = new Runnable() {
            public void run() {
                drawFrame();
            }
        };

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            mPaint.setARGB(76, 0, 0, 255);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            //设置处理触摸事件
            setTouchEventsEnabled(true);

        }

        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(drawTarget);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            mVisible = visible;
            if (visible) {
                //动态的绘制图形
                drawFrame();
            } else {
                //如果界面不可见，删除回调
                mHandler.removeCallbacks(drawTarget);
            }
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                                     float xStep, float yStep,
                                     int xPixels, int yPixels) {
            drawFrame();
        }

        public void onTouchEvent(MotionEvent event) {
            //如果检测到滑动操作
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mTouchX = event.getX();
                mTouchY = event.getY();
                Log.i("tag", "当前点击的位置是:x:" + mTouchX + " y:" + mTouchY);
            }else{
                mTouchX = -1;
                mTouchY = -1;
            }
            super.onTouchEvent(event);
        }

        //定义绘制图形的方法
        private void drawFrame() {
            //获取壁纸的surfaceHolder
            final SurfaceHolder holder = getSurfaceHolder();
            Canvas c = null;
            try {
                //开始编辑canvas,对画布加锁
                c = holder.lockCanvas();
                if (c != null) {
                    c.drawColor(0xffffffff);
                    //在触碰点绘制心形
                    drawTouchPoint(c);
                    mPaint.setAlpha(76);
                    c.translate(originX, originY);
                    //采用循环绘制count个矩形
                    for (int i = 0; i < count; i++) {
                        c.translate(80, 0);
                        c.scale(0.95f, 0.95f);
                        c.rotate(20f);
                        c.drawRect(0, 0, 150, 75, mPaint);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if (c != null) {
                    holder.unlockCanvasAndPost(c);
                }
            }
            mHandler.removeCallbacks(drawTarget);
            //调度下一次重绘
            if (mVisible) {
                count++;
                if (count >= 50) {
                    Random rand = new Random();
                    count = 1;
                    originX += (rand.nextInt(60) - 30);
                    originY += (rand.nextInt(60)-30);
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mHandler.postDelayed(drawTarget, 100);
            }
        }

        private void drawTouchPoint(Canvas c) {
            if (mTouchX >= 0 && mTouchY >= 0) {
                mPaint.setAlpha(255);
                c.drawBitmap(heart, mTouchX, mTouchY, mPaint);
            }
        }

    }


}


