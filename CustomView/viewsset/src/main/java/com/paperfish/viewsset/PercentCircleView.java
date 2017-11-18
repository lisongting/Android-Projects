package com.paperfish.viewsset;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

/**
 * 一个用来显示电量多少的圆形控件
 * Created by lisongting on 2017/11/14.
 */

public class PercentCircleView extends View {

    private int percent = 72;
    private int innerCircleRadius;
    private int innerCircleColor;
    private int textSize;
    private int textColor;
    private int strokeSize;
    private int strokeColor;
    private Paint innerCirclePaint;
    private Paint strokePaint;
    private Paint textPaint;

    private float centerX;
    private float centerY;

    private int colorBatteryLow = Color.parseColor("#ff8f00");
    private int colorBatteryVeryLow = Color.parseColor("#b71c1c");
    
    
    private Path bezierPath;
    private Paint bezierPaint;
    private float bezierStartX , bezierStartY;
    //贝塞尔曲线起始点的偏移量，用于动画效果
    private float bezierStartShift = 0;
    float peakHeight=0;
    float peakWidth =0;
    //用于裁剪的path
    private Path clipPath;

    public PercentCircleView(Context context) {
        this(context, null);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PercentCircleView, 0, 0);
        for (int i = 0; i < array.getIndexCount(); i++) {
            int index = array.getIndex(i);
            switch (index) {
                case R.styleable.PercentCircleView_inner_radius:
                    innerCircleRadius = array.getDimensionPixelSize(index, 200);
                    break;
                case R.styleable.PercentCircleView_inner_color:
                    innerCircleColor = array.getColor(index, Color.LTGRAY);
                    break;
                case R.styleable.PercentCircleView_stroke_size:
                    strokeSize = array.getDimensionPixelSize(index, 40);
                    break;
                case R.styleable.PercentCircleView_stroke_color:
                    strokeColor = array.getColor(index, Color.GREEN);
                    break;
                case R.styleable.PercentCircleView_text_size:
                    textSize = array.getDimensionPixelSize(index, 15);
                    break;
                case R.styleable.PercentCircleView_text_color:
                    textColor = array.getColor(index, Color.GREEN);
                    break;
                default:
                    break;
            }
        }

        initializePaint();

        array.recycle();

//        log("innderRadius:" + innerCircleRadius);
//        log("strokeSize:" + strokeSize);
//        log("textSize:" + textSize);
//        displayScreenInfo();

    }

    private void displayScreenInfo() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        log("screenSize:" + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels);
        log("density:"+displayMetrics.density);
        log("densityDpi:" + displayMetrics.densityDpi);

    }

    private void initializePaint() {
        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(innerCircleColor);
        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setStyle(Paint.Style.FILL);
        innerCirclePaint.setAlpha(0);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeSize);
        strokePaint.setAlpha(50);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);

        bezierPaint = new Paint();
        bezierPaint.setColor(Color.GREEN);
        bezierPaint.setStyle(Paint.Style.FILL);
        bezierPaint.setStrokeWidth(8);
        bezierPaint.setAntiAlias(true);

        bezierPath = new Path();

        clipPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.AT_MOST) {
//            int wantSize  = (innerCircleRadius + strokeSize) * 2 < widthSize ? (innerCircleRadius + strokeSize) * 2 : widthSize / 2;
//            width = wantSize < widthSize ? wantSize : widthSize;
            width = (innerCircleRadius + strokeSize) * 2;

        } else if (widthMode == MeasureSpec.EXACTLY) {
//            width = (innerCircleRadius + strokeSize) * 2 < widthSize ? (innerCircleRadius + strokeSize) * 2 : widthSize / 2;
            width = (innerCircleRadius + strokeSize) * 2;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
//            int wantSize = (innerCircleRadius + strokeSize) * 2 ;
//            height = wantSize < heightSize ? wantSize : heightSize;
            height = (innerCircleRadius + strokeSize) * 2 ;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = (innerCircleRadius + strokeSize) * 2 ;
        }

        //将width 和height 统一设置为更小的
//        height = width < height ? width : height;
        setMeasuredDimension(width, height);
        log("onMeasuredDimension:" + width + "x" + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        canvas.save();
        clipPath.reset();
        canvas.clipPath(clipPath);
        clipPath.addCircle(centerX, centerY, innerCircleRadius, Path.Direction.CCW);
        canvas.clipPath(clipPath, Region.Op.REPLACE);

        canvas.drawCircle(centerX, centerY, innerCircleRadius, innerCirclePaint);

        float left = centerX - innerCircleRadius - strokeSize/2;
        float top = centerY - innerCircleRadius - strokeSize/2;
        float right = centerX + innerCircleRadius + strokeSize/2;
        float bottom = centerY + innerCircleRadius + strokeSize/2;
        float sweepAngle = 360F * percent / 100F;

        if (percent <= 10) {
            strokePaint.setColor(colorBatteryVeryLow);
        } else if (percent <= 30) {
            strokePaint.setColor(colorBatteryLow);
        }else{
            strokePaint.setColor(strokeColor);
        }
        canvas.drawArc(left, top, right, bottom, 90, sweepAngle,false, strokePaint);



        //绘制贝塞尔曲线
        if (percent> 0 && percent < 50) {
            float distanceToCenter = 2*innerCircleRadius*(50 - percent)/100F  ;
            double angle = Math.acos(distanceToCenter / innerCircleRadius);
            peakWidth = (float) (innerCircleRadius * Math.sin(angle) / 2);
            peakHeight = (innerCircleRadius-distanceToCenter)/4;
            bezierStartX = centerX - 2 * peakWidth + bezierStartShift;
            bezierStartY = centerY + distanceToCenter;
        } else if (percent > 50 && percent <= 100) {
            float distanceToCenter;
            //如果等于100则按照99来算
            if (percent == 100) {
                distanceToCenter = 2 * innerCircleRadius * (99 - 50) / 100F;
            }
            distanceToCenter = 2 * innerCircleRadius * (percent - 50) / 100F;
            double angle = Math.acos(distanceToCenter / innerCircleRadius);
            peakWidth = (float) (innerCircleRadius * Math.sin(angle) / 2);
            peakHeight = (innerCircleRadius-distanceToCenter)/4;
            bezierStartX = centerX - 2 * peakWidth + bezierStartShift;
            bezierStartY = centerY - distanceToCenter;
        }else{
            peakWidth = innerCircleRadius / 2;
            peakHeight = (innerCircleRadius)/4;
            bezierStartX = centerX - 2 * peakWidth + bezierStartShift;
            bezierStartY = centerY ;
        }


        bezierPath.reset();

        bezierPath.moveTo(bezierStartX-4*peakWidth, bezierStartY);
        //画n组曲线，一组曲线为一个波峰和一个波谷
        int n =4;

        for(int i=0;i<n;i++) {
            float pAx = bezierStartX + (i-1) * peakWidth * 2;
//            float pAx = bezierStartX;
            float pAy = bezierStartY;

            float cABx = pAx + peakWidth / 2;
            float cABy = pAy - peakHeight;

            float pBx = pAx + peakWidth;
            float pBy = pAy;

            float cBCx = pBx + peakWidth / 2;
            float cBCy = pBy + peakHeight;

            float pCx = pAx + peakWidth*2;
            float pCy = pAy;

            bezierPath.quadTo(cABx, cABy, pBx, pBy);
            bezierPath.quadTo(cBCx,cBCy,pCx,pCy);
            //如果画到最后一组曲线，则将这个曲线的围成的图形进行封闭
            if (i == n-1 ) {
                bezierPath.lineTo(pCx+getMeasuredWidth(),pCy);
                bezierPath.lineTo(pCx+getMeasuredWidth(),pCy+(float)getMeasuredHeight());
                bezierPath.lineTo(bezierStartX-getMeasuredWidth() ,bezierStartY+(float)getMeasuredHeight());
                bezierPath.lineTo(bezierStartX-getMeasuredWidth(),bezierStartY);
                bezierPath.close();

            }
        }

        canvas.drawPath(bezierPath,bezierPaint);


        canvas.restore();

        String text = percent + "%";

        float textWidth = textPaint.measureText(text);

        canvas.drawText(text, centerX - textWidth / 2,centerY-(textPaint.ascent()+textPaint.descent())/2, textPaint);

    }

    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, peakWidth*2 );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bezierStartShift = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(1500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    public void setPercent(int p) {
        if (p < 0) {
            percent = 1;
        } else if (p > 100) {
            percent = 100;
        } else {
            this.percent = p;
        }

        postInvalidate();
        startAnim();
    }

    public int getPercent() {
        return percent;
    }

    private void log(String s) {
        Log.i("tag","PercenteCircleView -- "+ s);
    }

}
