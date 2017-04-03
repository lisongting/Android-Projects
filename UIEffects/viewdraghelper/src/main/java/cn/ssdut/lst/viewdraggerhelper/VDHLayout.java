package cn.ssdut.lst.viewdraggerhelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/1.
 */

public class VDHLayout extends LinearLayout {
    private ViewDragHelper mDragger;
    private ViewDragHelper.Callback callBack;
    private TextView tv1,tv2,tv3;
    private Point initPointPosition = new Point();

    protected void onFinishInflate() {
        tv1 = (TextView) findViewById(R.id.id_tv1);
        tv2 = (TextView) findViewById(R.id.id_tv2);
        tv3 = (TextView) findViewById(R.id.id_tv3);
        super.onFinishInflate();
    }
    public VDHLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        callBack = new DraggerCallBack();
        mDragger = ViewDragHelper.create(this, 1.0f, callBack);

    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initPointPosition.x = tv3.getLeft();
        initPointPosition.y = tv3.getTop();
    }
    class DraggerCallBack extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //如果是第二个textView，则返回false，不能拖动
            if (child.getId() == R.id.id_tv2) {
                return false;
            }
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int botBound = getHeight() - child.getHeight() - topBound;
            return Math.min(Math.max(top, topBound), botBound);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - child.getWidth() - leftBound;

            return Math.min(Math.max(left,leftBound),rightBound);
        }

        public void onViewReleased(View releasedChild, float xVal, float yVal) {
            //松手的时候，判断是不是第三个TextView如果是，则让它回去原位置
            if (releasedChild.getId() == R.id.id_tv3) {
                mDragger.settleCapturedViewAt(initPointPosition.x, initPointPosition.y);
                invalidate();
            }
        }
    }

    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }
}
