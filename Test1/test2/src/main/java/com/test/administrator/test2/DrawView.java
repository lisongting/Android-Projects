package com.test.administrator.test2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/9/6.
 */
public class DrawView extends View {
    public float currentX = 40;
    public float currentY = 50;
    Paint p = new Paint();
    public DrawView(Context context){
        super(context);
    }
    public DrawView(Context context, AttributeSet set){
        super(context,set);
    }
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        p.setColor(Color.YELLOW);
        canvas.drawCircle(currentX,currentY,15,p);
    }

    public boolean onTouchEvent(MotionEvent event){
        currentX = event.getX();
        currentY = event.getY();
        invalidate();
        return true;
    }
}