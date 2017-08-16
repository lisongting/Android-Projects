package com.paperfish.espresso.component;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;

import com.paperfish.espresso.util.DensityUtil;

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

    public void setBgColor(int color) {
        mBackgroundPaint.setColor(color);
        mRecyclerView.invalidate(mBgBounds);
    }

    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        mRecyclerView.invalidate(mBgBounds);
    }

    public void setTextSize(int size) {
        mTextPaint.setTextSize(size);
        mRecyclerView.invalidate(mBgBounds);
    }

    public void setBackgroundSize(int size) {
        mBackgroundSize = size;
        mCornerRadius = mBackgroundSize / 2;
        mRecyclerView.invalidate(mBgBounds);
    }

    public void setTypeface(Typeface typeface) {
        mTextPaint.setTypeface(typeface);
        mRecyclerView.invalidate(mBgBounds);
    }


    public void animateVisibility(boolean visible) {
        if (mVisible != visible) {
            mVisible = visible;
            if (mAlphaAnimator != null) {
                mAlphaAnimator.cancel();
            }
            mAlphaAnimator = ObjectAnimator.ofFloat(this, "alpha", visible ? 1F : 0F);

            mAlphaAnimator.setDuration(visible ? 200 : 150);
            mAlphaAnimator.start();
        }
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
        mRecyclerView.invalidate(mBgBounds);
    }

    public float getAlpha() {
        return mAlpha;
    }

    public void draw(Canvas canvas) {
        if (isVisible()) {
            int restoreCount = canvas.save(Canvas.MATRIX_SAVE_FLAG);

            canvas.translate(mBgBounds.left, mBgBounds.top);
            mTmpRect.set(mBgBounds);
            mTmpRect.offsetTo(0, 0);

            mBackgroundPath.reset();
            mBackgroundRect.set(mTmpRect);

            float[] radii;
            if (mRes.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                radii = new float[]{mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0};
            } else {

                radii = new float[]{mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0, mCornerRadius, mCornerRadius};
            }

            mBackgroundPath.addRoundRect(mBackgroundRect, radii, Path.Direction.CW);

            mBackgroundPaint.setAlpha((int) (mAlpha * 255));
            mTextPaint.setAlpha((int) (mAlpha * 255));
            canvas.drawPath(mBackgroundPath, mBackgroundPaint);
            canvas.drawText(mSectionName, (mBgBounds.width() - mTextBounds.width()) / 2,
                    mBgBounds.height() - (mBgBounds.height() - mTextBounds.height()) / 2,
                    mTextPaint);
            canvas.restoreToCount(restoreCount);
        }
    }

    public void setSectionName(String sectionName) {
        if (!sectionName.equals(mSectionName)) {
            mSectionName = sectionName;
            mTextPaint.getTextBounds(sectionName, 0, sectionName.length(), mTextBounds);
            mTextBounds.right = (int) (mTextBounds.left + mTextPaint.measureText(sectionName));
        }
    }

    //更新FastScroller的边界
    public Rect updateFastScrollerBounds(FastScrollRecyclerView recyclerView, int thumbOffsetY) {
        mInvalidateRect.set(mBgBounds);

    }

    private boolean isVisible() {
        return (mAlpha > 0f) && (!TextUtils.isEmpty(mSectionName));
    }
}
