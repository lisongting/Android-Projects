package com.paperfish.espresso.component;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.paperfish.espresso.R;
import com.paperfish.espresso.interfaces.OnFastScrollStateChangeListener;
import com.paperfish.espresso.util.DensityUtil;

/**
 * Created by lisongting on 2017/8/11.
 */

public class FastScroller {
    private static final int DEFAULT_AUTO_HIDE_DELAY = 1500;

    private FastScrollRecyclerView mRecyclerView ;
    private FastScrollPopup mPopup;

    private int mThumbHeight;
    private int mWidth;
    private Paint mThumb;
    private Paint mTrack;

    private Rect mTmpRect = new Rect();
    private Rect mInvalidateRect = new Rect();
    private Rect mInvalidateTmpRect = new Rect();

    private int mTouchInset;
    private int mTouchOffset;

    private Point mThumbPosition = new Point(-1, -1);
    private Point mOffset = new Point(0, 0);

    private boolean mIsDragging;

    private Animator mAutoHideAnimator;
    private boolean mAnimatingShow;
    private int mAutoHideDelay = DEFAULT_AUTO_HIDE_DELAY;
    private boolean mAutoHideEnabled = true;
    private final Runnable mHideRunnable;

    public FastScroller(Context context, FastScrollRecyclerView recyclerView, AttributeSet attributeSet) {
        Resources resources = context.getResources();

        mRecyclerView = recyclerView;
        mPopup = new FastScrollPopup(resources, recyclerView);

        mThumbHeight = DensityUtil.dip2px(context, 48);
        mWidth = DensityUtil.dip2px(context, 8);

        mTouchInset = DensityUtil.dip2px(context, -24);

        mThumb = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrack = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.FastScrollRecyclerView, 0, 0);

        mAutoHideEnabled = typedArray.getBoolean(R.styleable.FastScrollRecyclerView_fastScrollAutoHide, true);
        mAutoHideDelay = typedArray.getInteger(R.styleable.FastScrollRecyclerView_fastScrollAutoHideDelay, DEFAULT_AUTO_HIDE_DELAY);

        int trackColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_fastScrollTrackColor, 0x1f000000);
        int thumbColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_fastScrollThumbColor,
                ContextCompat.getColor(context, R.color.colorPrimary));
        int popupBgColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_fastScrollPopupBgColor,
                ContextCompat.getColor(context, R.color.colorPrimary));
        int popupTextColor = typedArray.getColor(R.styleable.FastScrollRecyclerView_fastScrollPopupTextColor, 0xffffffff);
        int popupTextSize = typedArray.getDimensionPixelSize(R.styleable.FastScrollRecyclerView_fastScrollPopupTextSize,
                DensityUtil.dip2sp(context,56));
        int popupBackgroundSize = typedArray.getDimensionPixelSize(R.styleable.FastScrollRecyclerView_fastScrollPopupBackgroundSize,
                DensityUtil.dip2px(context,88));

        mTrack.setColor(trackColor);
        mThumb.setColor(thumbColor);
        mPopup.setBgColor(popupBgColor);
        mPopup.setTextColor(popupTextColor);
        mPopup.setTextSize(popupTextSize);
        mPopup.setBackgroundSize(popupBackgroundSize);

        mHideRunnable = new Runnable() {
            @Override
            public void run() {
                if (!mIsDragging) {
                    if (mAutoHideAnimator != null) {
                        mAutoHideAnimator.cancel();
                    }
                    //如果RecyclerView的Layout是从右到左的，则
                    mAutoHideAnimator = ObjectAnimator.ofInt(FastScroller.this, "offsetX",
                            (mRecyclerView.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL ? -1 : 1) * mWidth);

                    mAutoHideAnimator.setInterpolator(new FastOutLinearInInterpolator());
                    mAutoHideAnimator.setDuration(200);
                    mAutoHideAnimator.start();
                }
            }
        };

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //只要一滚动视图，就把滚动条展现出来
                show();
            }
        });

        if (mAutoHideEnabled) {
            postAutoHideDelayed();
        }
    }

    public int getThumbHeight() {
        return mThumbHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public boolean isDragging() {
        return mIsDragging;
    }

    public void handleTouchEvent(MotionEvent event, int downX, int downY, int lastY, OnFastScrollStateChangeListener stateChangeListener) {
        ViewConfiguration config = ViewConfiguration.get(mRecyclerView.getContext());

        int action = event.getAction();
        int y = (int) event.getY();
    }
    private void postAutoHideDelayed() {

    }
}
