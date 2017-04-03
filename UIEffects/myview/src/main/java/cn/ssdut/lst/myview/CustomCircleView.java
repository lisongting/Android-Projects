package cn.ssdut.lst.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2017/3/30.
 */

public class CustomCircleView extends View {
    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private Paint mPaint;
    private int mProgress;
    private int mSpeed;
    private boolean isNext = false;

    public CustomCircleView(Context context) {
        this(context,null);
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyle, 0);
        int n = ta.getIndexCount();
        for(int i=0;i<n;i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = ta.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = ta.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    //
                    mCircleWidth = ta.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = ta.getInt(attr, 20);
                    break;
            }

        }
        ta.recycle();
        mPaint = new Paint();
        new Thread(new DrawRunnable()).start();
    }
    public void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = center - mCircleWidth / 2;
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        if (!isNext) {
            //第一个颜色是填充完整圆形，第二个颜色作为进度
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mSecondColor);
            //根据进度绘制圆弧
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }else{
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);

        }
    }
    //更新进度条的线程
    public class DrawRunnable implements Runnable{
        public void run() {
            while (true) {
                mProgress++;
                if (mProgress == 360) {
                    mProgress = 0;
                    if (!isNext) {
                        isNext = true;
                    }else{
                        isNext = false;
                    }
                }
                postInvalidate();
                try {
                    Thread.sleep(mSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
