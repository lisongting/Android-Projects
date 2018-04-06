package com.lst.botdialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongting on 2018/4/3.
 * 用来放置平面地图
 */

public class MapView extends View{

    private Matrix matrix;
    private Bitmap map;
    private Paint p,indicatorPaint,textPaint;
    private float indicatorRadius = 30;
    private Path path ;
    private Rect textBound;

    private List<Indicator> indicators;
    private int indicatorCount;
    //表示当前是否还在
    private boolean isTouching;

    public MapView(Context context) {
        this(context,null);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        matrix = new Matrix();
        indicators = new ArrayList<>();
        p = new Paint();
        path = new Path();
        textPaint = new Paint();
        textBound = new Rect();
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize((float) (indicatorRadius*1.5));
        textPaint.setAntiAlias(true);

        indicatorPaint = new Paint();
        indicatorPaint.setColor(Color.GREEN);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setAntiAlias(true);
        map = BitmapFactory.decodeResource(getResources(), R.drawable.map);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = map.getWidth();
        int height = map.getHeight();
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(map, matrix, p);

        for(int i=0;i<indicators.size();i++) {
            Indicator indicator = indicators.get(i);
            float centerX = indicator.getX();
            float centerY = indicator.getY();
            float theta = indicator.getTheta();

            path.reset();
            //画圆
            canvas.drawCircle(centerX, centerY, indicatorRadius, indicatorPaint);
            if (Math.abs(theta) < Math.PI / 2) {
                //将path移动到三个点,围成一个等腰三角形
                path.moveTo(centerX - indicatorRadius * (float) Math.sin(theta),
                        centerY - indicatorRadius * (float)Math.cos(theta));
                path.lineTo(centerX + indicatorRadius * (float) Math.sin(theta),
                        centerY + indicatorRadius * (float) Math.cos(theta));
                path.lineTo(centerX + 3 * indicatorRadius * (float) Math.cos(theta),
                        centerY - 3 * indicatorRadius * (float) Math.sin(theta));
            }else {
                theta = (float) (Math.PI - theta);
                path.moveTo(centerX - indicatorRadius * (float) Math.sin(theta),
                        centerY + indicatorRadius * (float)Math.cos(theta));
                path.lineTo(centerX + indicatorRadius * (float) Math.sin(theta),
                        centerY - indicatorRadius * (float) Math.cos(theta));
                path.lineTo(centerX - 3 * indicatorRadius * (float) Math.cos(theta),
                        centerY - 3 * indicatorRadius * (float) Math.sin(theta));
            }
            path.close();
            //画三角形
            canvas.drawPath(path, indicatorPaint);
            //绘制文字
            String text = String.valueOf(i+1);
            textPaint.getTextBounds(text, 0, text.length(), textBound);
            canvas.drawText(text, 0, text.length(), centerX - textBound.width() / 2, centerY + textBound.height() / 2, textPaint);
        }



        log("size:" + getMeasuredWidth() + "," + getMeasuredHeight());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//        Toast.makeText(getContext(), "pos:" + event.getX() + "," + event.getY(), Toast.LENGTH_SHORT).show();
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Indicator indicator = new Indicator(event.getX(), event.getY(), 0);
                indicators.add(indicator);
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:

                break;
            default:
                break;
        }

        return true;
    }

    private void log(String s) {
        Log.i("MapView", s);
    }


}
