package com.ssudt.lst.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lisongting on 2017/12/5.
 * 自定义ViewGroup练习
 */

public class MyViewGroup extends ViewGroup {


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width ;
        int height ;
        int cCount = getChildCount();
        //子view的宽高
        int cWidth ;
        int cHeight ;
        MarginLayoutParams cParams = null;
        //用于计算左边两个childView的高度
        int lHeight = 0;
        //用于计算右边两个childView的高度
        int rHeight = 0;
        //计算上面两个childView的宽度
        int tWidth = 0;
        //计算下面两个childView的宽度
        int bWidth = 0;

        for(int i =0;i<cCount;i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();
            switch (i) {
                case 0:
                    tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
                    lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
                    log("childView1 paddingLeft:" + childView.getPaddingLeft());
                    log("childView1 paddingTop:" + childView.getPaddingTop());
                    break;
                case 1:
                    tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
                    rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
                    break;
                case 2:
                    bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
                    lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
                    break;
                case 3:
                    bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
                    rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
                    break;
                default:
                    break;
            }
        }
        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width,
                (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        log("onMeasuredDimension:" + getMeasuredWidth() + "x" + getMeasuredHeight());
        for(int i=0;i<cCount;i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();
            //子view的左，上，右，下的坐标
            int cLeft = 0, cRight = 0, cTop = 0, cBottom = 0;
            switch (i) {
                case 0:
                    cLeft = cParams.leftMargin;
                    cTop = cParams.topMargin;
                    break;
                case 1:
                    cLeft = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    cTop = cParams.topMargin;
                    break;
                case 2:
                    cLeft = cParams.leftMargin;
                    cTop = getHeight() - cHeight - cParams.topMargin - cParams.bottomMargin;
                    break;
                case 3:
                    cLeft = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    cTop = getHeight() - cHeight - cParams.topMargin - cParams.bottomMargin;
                    break;
                default:
                    break;
            }
            cRight = cLeft + cWidth;
            cBottom = cTop + cHeight;
            childView.layout(cLeft, cTop,cRight, cBottom );
            log("child:" + i + " -- " + cLeft + "," + cTop + "," + cRight + "," + cBottom);
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    private void log(String s) {
        Log.i("tag", s);
    }
}
