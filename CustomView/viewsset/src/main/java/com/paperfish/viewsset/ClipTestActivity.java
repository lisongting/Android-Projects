package com.paperfish.viewsset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lisongting on 2017/11/18.
 */

public class ClipTestActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }
    private static class SampleView extends View {
        private Paint mPaint;
        private Path mPath;
        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(6);
            mPaint.setTextSize(20);
            mPaint.setTextAlign(Paint.Align.RIGHT);

            mPath = new Path();
        }

        private void drawScene(Canvas canvas) {
            canvas.clipRect(0, 0, 300, 300);

            canvas.drawColor(Color.WHITE);

            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 300, 300, mPaint);

            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(90, 210, 90, mPaint);

            mPaint.setColor(Color.BLUE);
            canvas.drawText("Clipping", 300, 90, mPaint);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.GRAY);
            canvas.save();
            canvas.translate(30, 30);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(480, 30);
            canvas.clipRect(30, 30, 270, 270);
            canvas.clipRect(90, 90, 210, 210, Region.Op.DIFFERENCE);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(30, 480);
            mPath.reset();
            canvas.clipPath(mPath); // makes the clip empty
            mPath.addCircle(150, 150, 150, Path.Direction.CCW);
            canvas.clipPath(mPath, Region.Op.REPLACE);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(480, 480);
            canvas.clipRect(0, 0, 180, 180);
            canvas.clipRect(120, 120, 300, 300, Region.Op.UNION);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(30, 930);
            canvas.clipRect(0, 0, 180, 180);
            canvas.clipRect(120, 120, 300, 300, Region.Op.XOR);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(480, 930);
            canvas.clipRect(0, 0, 180, 180);
            canvas.clipRect(120, 120, 300, 300, Region.Op.REVERSE_DIFFERENCE);
            drawScene(canvas);
            canvas.restore();
        }
    }
}