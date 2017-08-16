package com.paperfish.espresso.component;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

import com.paperfish.espresso.R;
import com.paperfish.espresso.interfaces.OnFastScrollStateChangeListener;
import com.paperfish.espresso.util.DensityUtil;

import static android.R.attr.maxHeight;
import static android.R.attr.x;
import static android.R.attr.y;

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
                    //如果RecyclerView的Layout是从右到左的，则将offsetX移动至-1*mWidth。
                    // 实际上就是从屏幕右边界外面移入屏幕
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

    //处理MotionEvent，并决定是否更新fastScroller
    public void handleTouchEvent(MotionEvent event, int downX, int downY, int lastY, OnFastScrollStateChangeListener stateChangeListener) {
        ViewConfiguration config = ViewConfiguration.get(mRecyclerView.getContext());

        int action = event.getAction();
        //获取当前点击位置相对于其父View的y坐标
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isNearPoint(downX, downY)) {
                    mTouchOffset = downY - mThumbPosition.y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //检查是否开始滚动
                if (!mIsDragging && isNearPoint(downX, downY) &&
                        Math.abs(y - downY) > config.getScaledTouchSlop()) {
                    //请求父View不要对点击事件进行拦截
                    mRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
                    mIsDragging = true;
                    mTouchOffset =mTouchOffset+ lastY - downY;
                    mPopup.animateVisibility(true);
                    if (stateChangeListener != null) {
                        stateChangeListener.onFastScroolStart();
                    }
                }
                if (mIsDragging) {
                    int top = 0;
                    int bottom = mRecyclerView.getHeight() - mThumbHeight;
                    float boundedY = (float) Math.max(top, Math.min(bottom, y - mTouchOffset));
                    String sectionName = mRecyclerView.scrollToPositionAtProgress((boundedY - top) / (bottom - top));
                    mPopup.setSectionName(sectionName);
                    mPopup.animateVisibility(!sectionName.isEmpty());
                    mRecyclerView.invalidate(mPopup.updateFastScrollerBounds(mRecyclerView, mThumbPosition.y));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTouchOffset = 0;
                if (mIsDragging) {
                    mIsDragging = false;
                    mPopup.animateVisibility(false);
                    if (stateChangeListener != null) {
                        stateChangeListener.onFastScroolStop();
                    }
                }
                break;
        }
    }

    public void draw(Canvas canvas) {
        if (mThumbPosition.x < 0 || mThumbPosition.y < 0) {
            return;
        }
        //Background
        canvas.drawRect(mThumbPosition.x + mOffset.x, mThumbHeight / 2 + mOffset.y, mThumbPosition.x
                + mOffset.x + mWidth, mRecyclerView.getHeight() + mOffset.y - mThumbHeight / 2, mTrack);

        //Handle
        canvas.drawRect(mThumbPosition.x + mOffset.x, mThumbPosition.y + mOffset.y, mThumbPosition.x
                + mOffset.x + mWidth, mThumbPosition.y + mOffset.y + mThumbHeight, mThumb);

        mPopup.draw(canvas);
    }

    private boolean isNearPoint(int downX, int downY) {
        mTmpRect.set(mThumbPosition.x, mThumbPosition.y, mThumbPosition.x + mWidth, mThumbPosition.y + maxHeight);

        mTmpRect.inset(mTouchInset, mTouchInset);
        return mTmpRect.contains(x, y);
    }



    public void setThumbPosition(int x, int y) {
        if (mThumbPosition.x == x && mThumbPosition.y == y) {
            return ;
        }
        mInvalidateRect.set(mThumbPosition.x + mOffset.x, mOffset.y, mThumbPosition.x + mOffset.x + mWidth, mRecyclerView.getHeight() + mOffset.y);
        mThumbPosition.set(x, y);
        mInvalidateTmpRect.set(mThumbPosition.x + mOffset.x, mOffset.y, mThumbPosition.x + mOffset.x + mWidth, mRecyclerView.getHeight() + mOffset.y);
        mInvalidateRect.union(mInvalidateTmpRect);
        mRecyclerView.invalidate(mInvalidateRect);
    }

    private void setOffset(int x, int y) {
        if (mOffset.x == x && mOffset.y == y) {
            return;
        }
        mInvalidateRect.set(mThumbPosition.x + mOffset.x, mOffset.y, mThumbPosition.x + mOffset.x + mWidth, mRecyclerView.getHeight() + mOffset.y);
        mOffset.set(x, y);
        mInvalidateTmpRect.set(mThumbPosition.x + mOffset.x, mOffset.y, mThumbPosition.x + mOffset.x + mWidth, mRecyclerView.getHeight() + mOffset.y);
        mInvalidateRect.union(mInvalidateTmpRect);
        mRecyclerView.invalidate(mInvalidateRect);
    }

    public void setOffsetX(int x) {
        setOffset(x, mOffset.y);
    }

    public int getOffsetX() {
        return mOffset.x;
    }

    private void show() {
        if (!mIsDragging) {
            if (mAutoHideAnimator != null) {
                mAutoHideAnimator.cancel();
            }
            mAutoHideAnimator = ObjectAnimator.ofInt(this, "offsetX", 0);
            mAutoHideAnimator.setInterpolator(new LinearInterpolator());
            mAutoHideAnimator.setDuration(150);
            mAutoHideAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mAnimatingShow = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    mAnimatingShow = false;
                }
            });
            mAnimatingShow = true;
            mAutoHideAnimator.start();
        }
        if (mAutoHideEnabled) {
            postAutoHideDelayed();
        } else {
            cancelAutoHide();
        }

    }

    private void postAutoHideDelayed() {
        if (mRecyclerView != null) {
            cancelAutoHide();
            mRecyclerView.postDelayed(mHideRunnable, mAutoHideDelay);
        }
    }

    private void cancelAutoHide() {
        if (mRecyclerView != null) {
            mRecyclerView.removeCallbacks(mHideRunnable);
        }
    }

    public void setThumbColor(@ColorInt int color) {
        mThumb.setColor(color);
        mRecyclerView.invalidate(mInvalidateRect);
    }

    public void setTrackColor(@ColorInt int color) {
        mTrack.setColor(color);
        mRecyclerView.invalidate(mInvalidateRect);
    }

    public void setPopupBgColor(@ColorInt int color) {
        mPopup.setBgColor(color);
    }

    public void setPopupTextColor(@ColorInt int color) {
        mPopup.setTextColor(color);
    }

    public void setPopupTypeface(Typeface typeface) {
        mPopup.setTypeface(typeface);
    }

    public void setPopupTextSize(int size) {
        mPopup.setTextSize(size);
    }

    public void setAutoHideDelay(int hideDelay) {
        mAutoHideDelay = hideDelay;
        if (mAutoHideEnabled) {
            postAutoHideDelayed();
        }
    }

    public void setAutoHideEnabled(boolean autoHideEnabled) {
        mAutoHideEnabled = autoHideEnabled;
        if (autoHideEnabled) {
            postAutoHideDelayed();
        } else {
            cancelAutoHide();
        }
    }

}
