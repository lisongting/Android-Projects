package com.paperfish.espresso.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.paperfish.espresso.interfaces.OnFastScrollStateChangeListener;

/**
 * Created by lisongting on 2017/8/11.
 */

public class FastScrollRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener{

    private FastScroller mScrollbar;


    public static class ScrollPositionState{
        //第一个可见行的下标
        public int rowIndex;

        //从第一个可见行往下的偏移
        public int rowTopOffset;

        public int rowHeight;
    }

    private int mDownX;
    private int mDownY;
    private int mLastY;

    private OnFastScrollStateChangeListener mStateChangeListener;


    public FastScrollRecyclerView(Context context) {
        this(context,null);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScrollbar = new FastScroller(context, this, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
