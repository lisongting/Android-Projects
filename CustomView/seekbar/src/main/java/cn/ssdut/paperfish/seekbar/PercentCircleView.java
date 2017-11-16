package cn.ssdut.paperfish.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by lisongting on 2017/11/14.
 */

public class PercentCircleView extends View {

    private int percent = 56;
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

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeSize);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);

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
        String text = percent + "%";

        float textWidth = textPaint.measureText(text);

        canvas.drawText(text, centerX - textWidth / 2,centerY-(textPaint.ascent()+textPaint.descent())/2, textPaint);


        //----------------------------------------
        //measureText()获取到的是整个字符所占的大矩形宽度(包含预留空间)
        //fontMetrics表示整个字符串的大矩形宽度（高度为从top到bottom 的距离）
//        log("center point :" + centerX + "," + centerY);
//        log("measureText() width:" + textWidth);
//        log("textPaint ascent:" + textPaint.ascent() + ", descent:" + textPaint.descent());
//        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
//        log("fontMetrics : " + fontMetrics.top + " ," + fontMetrics.bottom + "," + fontMetrics.ascent + "," + fontMetrics.descent);
//        log("fontMetrics height:" + String.valueOf(fontMetrics.bottom - fontMetrics.top));
//
//        //textBound是字符串实际占用的空间(不包含预留空间，高度为从ascent到decent的距离)
//        Rect rect = new Rect();
//        textPaint.getTextBounds(text, 0, 3, rect);
//        log("TextBounds Rect:" + rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom);
//        log("textBounds Size:" + rect.width() + "x" + rect.height());
    }

    public void setPercent(int p) {
        if (p < 0) {
            percent = 0;
        } else if (p > 100) {
            percent = 100;
        } else {
            this.percent = p;
        }

        postInvalidate();
    }

    public int getPercent() {
        return percent;
    }

    private void log(String s) {
        Log.i("tag","PercenteCircleView -- "+ s);
    }

}
