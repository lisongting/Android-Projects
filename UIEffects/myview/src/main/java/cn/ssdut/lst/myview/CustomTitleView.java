package cn.ssdut.lst.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Administrator on 2017/3/30.
 */

public class CustomTitleView extends View {

    private String mText;
    private int mTextColor;
    private int mTextSize;

    private Rect mBound;
    private Paint mPaint;
    private Bitmap mImage;
    private int mImageScale;
    private Rect rect;

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //获得自定义的属性样式
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
        int n = ta.getIndexCount();
        for(int i=0;i<n;i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    mText = ta.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColr:
                    mTextColor = ta.getInt(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    //TypedArray把sp转换成px
                    mTextSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()
                    ));
                    break;

            }
        }
        ta.recycle();
        mPaint = new Paint();
        mBound = new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.descent();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制矩形
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        Toast.makeText(getContext(), "mBound width:"+mBound.width()+"  height:"+mBound.height(), Toast.LENGTH_SHORT).show();
        //绘制文字
        mPaint.setColor(mTextColor);
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mText = randomText();
                invalidate();
            }
        });
    }

    private String randomText() {
        Random ran = new Random();
        int randInt = ran.nextInt(9999);
        return String.valueOf(randInt);
    }

    //当在xml中设置了layout_width和layout_height都为wrap_content之后
    //要在onMeasure中进行对宽高进行测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else{
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else{
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingBottom() + textHeight + getPaddingTop());
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

}
