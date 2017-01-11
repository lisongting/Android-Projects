package cn.ssdut.lst.desktopwidget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;

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
        private int count =1;
        private int originX = 50,originY = 50;
        private Paint mPaint = new Paint();
        Handler mHandler = new Handler();
        //定义一个周期执行的任务
        private final Runnable drawTarget = new Runnable() {
            public void run() {
                drawFrame();
            }
        }
    }
}
