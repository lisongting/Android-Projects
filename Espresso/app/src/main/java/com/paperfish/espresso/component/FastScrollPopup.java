package com.paperfish.espresso.component;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by lisongting on 2017/8/11.
 */

public class FastScrollPopup {

    private FastScrollRecyclerView mRecyclerView;

    private Resources mRes;

    private int mBackgroundSize ;
    private int mCornerRadius;
    private Path mBackgroundPath = new Path();
    private RectF mBackgroundRect = new RectF();
    private Paint mBackgroundPaint;

    private Rect mInvalidateRect = new Rect();
    private Rect mTmpRect = new Rect();

    // The absolute bounds of the fast scroller bg
    private Rect mBgBounds = new Rect();

    private String mSectionName;

    private Paint mTextPaint;
    private Rect mTextBounds = new Rect();

    private float mAlpha = 1;

    private ObjectAnimator mAlphaAnimator;
    private boolean mVisible;

    public FastScrollPopup(Resources reources, FastScrollRecyclerView recyclerView) {
        mRes = reources;

        mRecyclerView = recyclerView;

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAlpha(0);

        setTextSize(DensityUtil.dip2sp(recyclerView.getContext(),56));
        setBackgroundSize(DensityUtil.dip2px(recyclerView.getContext(),88));
    }

}
