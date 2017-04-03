package cn.ssdut.lst.uieffects;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/4/2.
 */

public class CustomViewPager extends ViewPager {

    private View mLeft;
    private View mRight;
    private float mTrans;
    private float mScale;

    private static final float MIN_SCALE = 0.5F;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context);
    }

    protected void onPageScrolled(int position, float offset, int offsetPixels) {

    }
}
