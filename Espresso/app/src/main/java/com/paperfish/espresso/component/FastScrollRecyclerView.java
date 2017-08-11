package com.paperfish.espresso.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by lisongting on 2017/8/11.
 */

public class FastScrollRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener{


    public FastScrollRecyclerView(Context context) {
        super(context);
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
