package com.paperfish.espresso.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.paperfish.espresso.R;

/**
 * Created by lisongting on 2017/8/5.
 */

public class Timeline extends View {

    private int atomSize = 24;
    private int lineSize = 12;

    private Drawable startLine;
    private Drawable finishLine;
    private Drawable atomDrawable;


    public Timeline(Context context) {
        super(context);
    }

    public Timeline(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public Timeline(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Timeline);

        atomSize = typedArray.getDimensionPixelSize(R.styleable.Timeline_atom_size, atomSize);
        lineSize = typedArray.getDimensionPixelSize(R.styleable.Timeline_line_size, lineSize);

        startLine = typedArray.getDrawable(R.styleable.Timeline_start_line);
        finishLine = typedArray.getDrawable(R.styleable.Timeline_finish_line);
        atomDrawable = typedArray.getDrawable(R.styleable.Timeline_atom);

        if (startLine != null) {
            startLine.setCallback(this);
        }
        if (finishLine != null) {
            finishLine.setCallback(this);
        }
        if (atomDrawable != null) {
            atomDrawable.setCallback(this);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = getPaddingLeft() + getPaddingRight();
        int h = getPaddingBottom() + getPaddingTop();

        if (atomDrawable != null) {
            w += atomSize;
            h += atomSize;
        }
        w = Math.max(w, getMeasuredWidth());
        h = Math.max(h, getMeasuredWidth());

        //这里解析出合适的大小，实质是MeasureSpec的mode判断过程，第一个参数是期望的大小
        int width = resolveSizeAndState(w, widthMeasureSpec, 0);
        int height = resolveSizeAndState(h, heightMeasureSpec, 0);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawableSize();

    }

    private void initDrawableSize() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();
        int pTop = getPaddingTop();

        int width = getWidth();
        int height = getHeight();

        int cWidth = width - pLeft - pRight;
        int cHeight = height - pTop - pBottom;

        Rect bounds;
        if (atomDrawable != null) {
            int atomSize = Math.min(this.atomSize, Math.min(cWidth, cHeight));
            atomDrawable.setBounds(pLeft, pTop, pLeft + cWidth, pTop + cHeight);
            bounds = atomDrawable.getBounds();
        }else{
            bounds = new Rect(pLeft, pTop, pLeft + cWidth, pTop + cHeight);
        }

        int halfLineSize = lineSize / 2;
        int lineLeft = bounds.centerX() - halfLineSize;
        if (startLine != null) {
            startLine.setBounds(lineLeft, 0, lineLeft + lineSize, bounds.top);
        }
        if (finishLine != null) {
            finishLine.setBounds(lineLeft, bounds.bottom, lineLeft + lineSize, height);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (startLine != null) {
            startLine.draw(canvas);
        }
        if (finishLine != null) {
            finishLine.draw(canvas);
        }
        if (atomDrawable != null) {
            atomDrawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    public void setLineSize(int lineSize) {
        if (this.lineSize != lineSize) {
            this.lineSize = lineSize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setAtomSize(int atomSize) {
        if (this.atomSize != atomSize) {
            this.atomSize = atomSize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setStartLine(Drawable startLine) {
        if (this.startLine != startLine) {
            this.startLine = startLine;
            if (this.startLine != null) {
                this.startLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    public void setFinishLine(Drawable finishLine) {
        if (this.finishLine != finishLine) {
            this.finishLine = finishLine;
            if (this.finishLine != null) {
                this.finishLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    public void setAtomDrawable(Drawable atomDrawable) {
        if (this.atomDrawable != atomDrawable) {
            this.atomDrawable = atomDrawable;
            if (this.atomDrawable != null) {
                this.atomDrawable.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

}
