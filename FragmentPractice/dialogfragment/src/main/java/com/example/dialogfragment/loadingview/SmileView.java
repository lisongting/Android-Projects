package com.example.dialogfragment.loadingview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.dialogfragment.R;

/**
 * Created by lisongting on 2017/7/6.
 */

public class SmileView extends View {

    private int firstColor;
    private int mWidth,mHeight;
    private Paint mPaint;
    private ValueAnimator startAnimator,sweepAnimator,degreeAnimation,pointXAnimation,pointYAnimation,pointAnimation;
    private float startAngle,sweepAngle,degree,pointX,pointY,point;
    private AnimatorSet animatorSet;
    private boolean isShowSmile;

    public SmileView(Context context) {
        this(context,null);
    }

    public SmileView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.SmileView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for(int i=0;i<n;i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SmileView_viewColor:
                    firstColor = typedArray.getColor(attr, Color.BLUE);
                    break;
            }
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        startAngle = -180;
        sweepAngle = 270;
        degree = 0;

        typedArray.recycle();
    }

    //一个int是32位，前2位是测量模式，后面30位代表
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//这里拿到的是父View提供的参考大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i("tag", "onMeasure :" + widthSize + "x" + heightSize);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize *1 / 2;
        }
        //如果是EXACTLY,则父View对当前view的测量值就是这个View应该的尺寸
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //因为不会有UNSPECIFIED，这里测量模式只会是AT_MOST,heightSize就是当前View能取得最大值
            //因此将真实的height设置为最大值的一半
            height = heightSize *1 / 2;
        }
        mWidth = width;
        mHeight = height;
        pointX = mHeight / 3;
        pointY = 0;
        point = mHeight / 3;
        setMeasuredDimension(mWidth, mHeight);
        Log.i("tag", "onMeasureDimension:" + mWidth + "x" + mHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(firstColor);
        mPaint.setStrokeWidth(10);
        float r = mHeight / 3;
        if (isShowSmile) {
            canvas.drawPoint(-pointX, -pointY, mPaint);
            canvas.drawPoint(point, -pointY, mPaint);
        }
        canvas.rotate(degree);
        RectF rectf = new RectF(-r, -r, r, r);
        canvas.drawArc(rectf, startAngle, sweepAngle, false, mPaint);

    }

    public void startAnimation() {
        isShowSmile = false;
        startAngle = -180;
        sweepAngle = 270;
        degree = 0;
        startAnimator = ValueAnimator.ofFloat(-180, 0);
        startAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        sweepAnimator = ValueAnimator.ofFloat(270, 180);
        sweepAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        degreeAnimation = ValueAnimator.ofFloat(0, 360);
        degreeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        animatorSet = new AnimatorSet();
        animatorSet.play(startAnimator).with(sweepAnimator).after(degreeAnimation);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(1000);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("tag", "Animator.AnimatorListener -- onAnimationStart()");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("tag", "Animator.AnimatorListener -- onAnimationEnd()");
                isShowSmile = true;
                pointXAnimation.start();
                pointYAnimation.start();
                pointAnimation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i("tag", "Animator.AnimatorListener -- onAnimationCancel()");

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i("tag", "Animator.AnimatorListener -- onAnimationRepeat()");

            }
        });

        pointXAnimation = ValueAnimator.ofFloat(mHeight / 3, mHeight / 4);
        pointXAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointX = (float) animation.getAnimatedValue();
                invalidate();
            }

        });
        pointXAnimation.setDuration(1000);

        pointAnimation = ValueAnimator.ofFloat(mHeight / 3, mHeight / 4);
        pointAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                point = (float) animation.getAnimatedValue();
                invalidate();
            }

        });
        pointAnimation.setDuration(1000);

        pointYAnimation = ValueAnimator.ofFloat(0, mHeight / 4);
        pointYAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pointY = (float) animation.getAnimatedValue();
                invalidate();
            }

        });
        pointYAnimation.setDuration(1000);

    }

    public void endAnimation() {
        animatorSet.end();
        pointXAnimation.end();
        pointYAnimation.end();
        pointAnimation.end();
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        if (visibility != VISIBLE) {
//            endAnimation();
        }
    }
}
