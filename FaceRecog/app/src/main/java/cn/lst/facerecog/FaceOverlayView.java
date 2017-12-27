/*
 * Copyright 2017 lisongting
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.lst.facerecog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;

/**
 * This class is a simple View to display the faces.
 */
public class FaceOverlayView extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private int mDisplayOrientation =0;
    private int mOrientation;
    private int previewWidth;
    private int previewHeight;
    private FaceData[] mFaces;

    private DisplayMetrics displayMetrics;

    public FaceOverlayView(Context context) {
        super(context);
        initialize();
    }

    public FaceOverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        initialize();
    }

    private void initialize() {
        // We want a green box around the face:
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int stroke = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, metrics);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(stroke);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, metrics);
        mTextPaint.setTextSize(size);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setStyle(Paint.Style.FILL);

        displayMetrics = new DisplayMetrics();
    }

    public void setFaces(FaceData[] faces) {
        mFaces = faces;
        invalidate();
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public void setDisplayOrientation(int displayOrientation) {
        mDisplayOrientation = displayOrientation;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFaces != null && mFaces.length > 0) {
            float scaleX = (float) getWidth() / (float) previewWidth;
            float scaleY = (float) getHeight() / (float) previewHeight;
            switch (mDisplayOrientation) {
                case Surface.ROTATION_90:
                case Surface.ROTATION_270:
                    scaleX = (float) getWidth() / (float) previewHeight;
                    scaleY = (float) getHeight() / (float) previewWidth;
                    break;
            }

            canvas.save();
            canvas.rotate(-mOrientation);

            for (FaceData face : mFaces) {
                PointF mid;
                mid = face.getMidEye();
                if (mid.x != 0.0f && mid.y != 0.0f) {
                    float eyesDis = face.eyesDistance();
                    RectF rectF = ImageUtils.getDrawFaceRectF(mid,eyesDis,scaleX,scaleY);
                    canvas.drawRect(rectF, mPaint);
                }
            }
            canvas.restore();
        }
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }
}