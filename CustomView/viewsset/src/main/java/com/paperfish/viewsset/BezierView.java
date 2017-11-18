package com.paperfish.viewsset;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by lisongting on 2017/11/17.
 */

public class BezierView extends View {

    private Path path;

    private Paint paint;

    private float startX = 0;
    private float startY;

    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);

        path = new Path();

    }

    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, getMeasuredWidth()/2 );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        startX  = ;
        startY = getMeasuredHeight() / 2;

        float peakHeight = getMeasuredHeight() / 16;
        float peakWidth = getMeasuredWidth() / 4;

        path.reset();

        path.moveTo(startX - peakWidth * 2, startY);
        //画三个组曲线，一组曲线为一个波峰和一个波谷
        int n =3;

        for(int i=0;i<n;i++) {
            float pAx = startX + (i-1) * peakWidth * 2;
            float pAy = startY;

            float cABx = pAx + peakWidth / 2;
            float cABy = pAy - peakHeight;

            float pBx = pAx + peakWidth;
            float pBy = pAy;

            float cBCx = pBx + peakWidth / 2;
            float cBCy = pBy + peakHeight;

            float pCx = pAx + peakWidth*2;
            float pCy = pAy;

            path.quadTo(cABx, cABy, pBx, pBy);
            path.quadTo(cBCx,cBCy,pCx,pCy);
            //如果画到最后一组曲线，则将这个曲线的围成的图形进行封闭
            if (i == n-1 ) {
                path.lineTo(pCx,pCy+(float)getMeasuredHeight()/2);
                path.lineTo(startX-peakWidth*2 ,startY+(float)getMeasuredHeight()/2);
                path.lineTo(startX-peakWidth*2,startY);
                path.close();
            }
        }


        canvas.drawPath(path,paint);

    }




}
