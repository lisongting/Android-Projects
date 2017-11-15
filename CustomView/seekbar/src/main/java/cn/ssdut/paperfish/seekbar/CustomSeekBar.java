package cn.ssdut.paperfish.seekbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by lisongting on 2017/11/14.
 */

public class CustomSeekBar extends View {

    private boolean isHorizontal;
    private int trackLength,trackWidth,trackColor;
    private int indicatorRadius,bigIndicatorRadius,indicatorColor;
    private int bubbleColor,bubbleHeight,bubbleWidth,bubbleTextColor,bubbleTextSize;

    private int minValue;
    private int maxValue;
    private String minValueStr;
    private String maxValueStr;
    private int valueTextSize;
    private int valueTextColor;
    private Rect minTextBounds,maxTextBounds,bubbleTextBounds;

    private float triangleHeight;

    private Paint trackPaint;
    private Paint bubblePaint;
    private Paint indicatorPaint;
    private Paint valueTextPaint;
    private Paint bubbleTextPaint;
    private Path trianglePath;

    //这个进度为[0,100]，但实际显示时要转换为minValue到maxValue之间的数值
    private float progress = 21F;

    //当前滑块的x和y坐标
    private float posX;
    private float posY;

    private OnProgressChangeListener progressChangeListener;

    private float centerX;
    private float centerY;
    private float trackLeft;
    private float trackRight;
    private float trackTop;
    private float trackBottom;
    private boolean isIndicatorDragged = false;

    /**
     * 进度变化的监听器
     */
    public interface OnProgressChangeListener {
        /**
         * 当拖动进度发生变化时触发
         * @param value ：取值[minValue,maxValue]
         */
        void onProgressChanged(int value);

        /**
         * 当滑块拖动完毕时触发
         * @param value：取值[minValue,maxValue]
         */
        void onSeekCompleted(int value);
    }

    public CustomSeekBar(Context context){
        this(context,null);
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomSeekBar(Context context,AttributeSet attributeSet,int defStyleAttr) {
        this(context, attributeSet, defStyleAttr,0);
    }

    public CustomSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomSeekBar, defStyleAttr, defStyleRes);
        for(int i=0;i<typedArray.getIndexCount();i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.CustomSeekBar_is_horizontal:
                    isHorizontal = typedArray.getBoolean(index, false);
                    break;
                case R.styleable.CustomSeekBar_track_length:
                    trackLength = typedArray.getDimensionPixelSize(index,200);
                    break;
                case R.styleable.CustomSeekBar_track_width:
                    trackWidth = typedArray.getDimensionPixelSize(index, 10);
                    break;
                case R.styleable.CustomSeekBar_track_color:
                    trackColor = typedArray.getColor(index, Color.GREEN);
                    break;
                case R.styleable.CustomSeekBar_indicator_radius:
                    indicatorRadius = typedArray.getDimensionPixelSize(index, 20);
                    bigIndicatorRadius = (int) (indicatorRadius * 1.5);
                    break;
                case R.styleable.CustomSeekBar_indicator_color:
                    indicatorColor = typedArray.getColor(index, Color.BLUE);
                    break;
                case R.styleable.CustomSeekBar_bubble_color:
                    bubbleColor = typedArray.getColor(index, Color.CYAN);
                    break;
                case R.styleable.CustomSeekBar_bubble_height:
                    bubbleHeight = typedArray.getDimensionPixelSize(index, 20);
                    break;
                case R.styleable.CustomSeekBar_bubble_width:
                    bubbleWidth = typedArray.getDimensionPixelSize(index, 10);
                    break;
                case R.styleable.CustomSeekBar_bubble_text_size:
                    bubbleTextSize = typedArray.getDimensionPixelSize(index, 15);
                    break;
                case R.styleable.CustomSeekBar_bubble_text_color:
                    bubbleTextColor = typedArray.getColor(index, Color.BLUE);
                    break;
                case R.styleable.CustomSeekBar_min_value:
                    minValue = typedArray.getInteger(index, 0);
                    minValueStr = String.valueOf(minValue);
                    break;
                case R.styleable.CustomSeekBar_max_value:
                    maxValue = typedArray.getInteger(index, 100);
                    maxValueStr = String.valueOf(maxValue);
                    break;
                case R.styleable.CustomSeekBar_value_text_color:
                    valueTextColor = typedArray.getColor(index, Color.BLUE);
                    break;
                case R.styleable.CustomSeekBar_value_text_size:
                    valueTextSize = typedArray.getDimensionPixelSize(index, 15);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();

        initialize();
    }

    private void initialize() {
        trackPaint = new Paint();
        trackPaint.setAntiAlias(true);
        trackPaint.setStyle(Paint.Style.STROKE);
        trackPaint.setColor(trackColor);
        trackPaint.setStrokeWidth(trackWidth);
        trackPaint.setStrokeCap(Paint.Cap.ROUND);

        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setColor(indicatorColor);
        indicatorPaint.setStyle(Paint.Style.FILL);

        valueTextPaint = new TextPaint();
        valueTextPaint.setTextSize(valueTextSize);
        valueTextPaint.setColor(valueTextColor);
        valueTextPaint.setStyle(Paint.Style.FILL);

        minTextBounds = new Rect();
        valueTextPaint.getTextBounds(minValueStr,0,minValueStr.length(),minTextBounds);
        maxTextBounds = new Rect();
        valueTextPaint.getTextBounds(maxValueStr, 0, maxValueStr.length(), maxTextBounds);

        bubblePaint = new Paint();
        bubblePaint.setAntiAlias(true);
        bubblePaint.setColor(bubbleColor);
        bubblePaint.setStyle(Paint.Style.FILL);
        bubbleTextPaint = new Paint();
        bubbleTextPaint.setColor(bubbleTextColor);
        bubbleTextPaint.setTextSize(bubbleTextSize);
        bubbleTextPaint.setStyle(Paint.Style.FILL);
        bubbleTextBounds = new Rect();
        trianglePath = new Path();

    }


    @Override
    public void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        if (isHorizontal) {

//            if (widthMeasureSpec == MeasureSpec.EXACTLY) {
//                width += trackLength + indicatorRadius * 4;
//            } else if (widthMeasureSpec == MeasureSpec.AT_MOST) {
//                width += widthSize / 1.5;
//            }
//            height += indicatorRadius * 2 + bubbleHeight + triangleHeight * 2;
            triangleHeight = bigIndicatorRadius / 2;
            width += trackLength + bigIndicatorRadius * 2 ;

            height += bigIndicatorRadius * 2 +getPaddingBottom()+getPaddingTop()+maxTextBounds.height()*1.5;

        }else{

        }

        setMeasuredDimension(width, height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
        trianglePath.reset();
        if (isHorizontal) {
            trackLeft = centerX - trackLength / 2;
            trackRight = centerX + trackLength / 2;
            trackTop = centerY - trackWidth / 2;
            trackBottom = centerY + trackWidth / 2;
            canvas.drawLine(bigIndicatorRadius, centerY, trackLeft + trackLength, centerY, trackPaint);

            posX = trackLength * progress / 100F + bigIndicatorRadius;
            posY = centerY;
            if (isIndicatorDragged) {
                canvas.drawCircle(posX, posY, bigIndicatorRadius, indicatorPaint);
            }
            canvas.drawCircle(posX, posY, indicatorRadius, indicatorPaint);


            canvas.drawText(minValueStr, trackLeft - minTextBounds.width() / 2,
                    centerY + bigIndicatorRadius+ minTextBounds.height() * 1.5F, valueTextPaint);
            canvas.drawText(maxValueStr, trackRight - maxTextBounds.width() / 2,
                    centerY + bigIndicatorRadius + maxTextBounds.height() * 1.5F, valueTextPaint);
            if (isIndicatorDragged) {
                float triangleStartX = posX;
                float triangleStartY = posY - bigIndicatorRadius;
                trianglePath.moveTo(triangleStartX, triangleStartY);
                trianglePath.lineTo((float) (triangleStartX + triangleHeight * Math.tan(Math.PI / 6)), triangleStartY - triangleHeight);
                trianglePath.lineTo((float) (triangleStartX - triangleHeight * Math.tan(Math.PI / 6)), triangleStartY - triangleHeight);
                trianglePath.close();
                canvas.drawPath(trianglePath, bubblePaint);

                float bubbleRectLeft = triangleStartX - bubbleWidth/2;
                float bubbleRectTop = triangleStartY - triangleHeight - bubbleHeight;
                float bubbleRectRight =  triangleStartX + bubbleWidth/2;
                float bubbleRectBottom = triangleStartY - triangleHeight;
                canvas.drawRect(bubbleRectLeft, bubbleRectTop, bubbleRectRight, bubbleRectBottom, bubblePaint);

                String tip = String.valueOf(progressToRealValue(progress));
                bubbleTextPaint.getTextBounds(tip, 0, tip.length(), bubbleTextBounds);
                canvas.drawText(tip, triangleStartX - bubbleTextBounds.width() / 2, bubbleRectBottom - bubbleHeight /4, bubbleTextPaint);


            }
        } else {

        }
//        log("track length:" + trackLength);
//        log("track center :(" + centerX + "," + centerY + ")");
//        log("track rectangle:" + trackLeft+ "," + trackTop + "," + trackRight + "," + trackBottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        log("onTouchEvent xy:" + event.getX() +","+ event.getY());
//        log("raw xy:" + event.getRawX() + "," + event.getRawY());
        log("isIndicatorTouched :" + isIndicatorTouched(event));
        log("isTrackTouched:" + isTrackTouched(event));
        event.getAction();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                log("ACTIONDOWN");
                if (!isIndicatorTouched(event) && isTrackTouched(event)) {
                    float destProgress = 0;
                    if (isHorizontal) {
                        float x = event.getX();
                        float distance = x - trackLeft;
                        destProgress = distance / trackLength *100;
                        animateTo(destProgress);
                    }else{

                    }
                    if (progressChangeListener != null) {
                        progressChangeListener.onSeekCompleted(progressToRealValue(destProgress));
                    }
                    log("progress changed:" + progress);
                }
                if (isIndicatorTouched(event)) {
                    isIndicatorDragged = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:
//                log("ACTIONMOVE");
                if (isTrackTouched(event)) {
                    if (isHorizontal) {
                        float x = event.getX();
                        float distance = x - trackLeft;
                        progress = distance / trackLength *100;
                        invalidate();
                    }else {
                        float y = event.getY();
                    }
                    log("progress changed:" + progress);
                }
                break;
            case MotionEvent.ACTION_UP:
//                log("ACTIONUP");
                if (isIndicatorDragged) {
                    if (progressChangeListener != null) {
                        progressChangeListener.onSeekCompleted(progressToRealValue(progress));
                    }
                    isIndicatorDragged = false;
                }
                invalidate();
                break;
            default:
                break;

        }
        return true;

    }

    /**
     * 判定当前手指的位置是否和滑块相近
     * @param motionEvent
     * @return
     */
    private boolean isIndicatorTouched(MotionEvent motionEvent) {
        float xDelta = Math.abs(motionEvent.getX() - posX);
        float yDelta = Math.abs(motionEvent.getY() - posY);

        //把用来判断的distance变小一些，方便用户触摸
        float distance = xDelta * xDelta + yDelta * yDelta;

        return distance <= (indicatorRadius*2 * indicatorRadius*2);
    }

    /**
     * 是否在合理的进度条范围内
     * @param motionEvent
     * @return
     */
    private boolean isTrackTouched(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        if(isHorizontal && x>=trackLeft
                && x<=trackRight
                && y>=trackTop*0.3
                && y<=trackBottom*2){
            return true;
        }else{

        }
        return false;
    }

    /**
     * 滑块的动画效果
     * @param destProgress
     */
    private void animateTo(float destProgress) {
        float currentProgress = progress;
        ValueAnimator animator = ValueAnimator.ofFloat(currentProgress, destProgress);
        if (isHorizontal) {
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    progress = value;
                    invalidate();
                }
            });
        } else {

        }

        animator.setDuration(400);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public void setOnSeekChangeListener(@NonNull  OnProgressChangeListener listener) {
        this.progressChangeListener = listener;
    }


    public void setProgress(float progress) {
        this.progress = progress;
        animateTo(progress);
    }

    /**
     *
     * @param progress 表示滑动条的百分比
     * @return 实际代表的含义数值，在minValue到maxValue之间
     */
    private int progressToRealValue(float progress) {
        int gap = maxValue - minValue;
        return Math.round (gap * progress / 100 + minValue);
    }

    private void log(String s) {
        Log.i("tag,","SeekBar -- "+ s);
    }




}
