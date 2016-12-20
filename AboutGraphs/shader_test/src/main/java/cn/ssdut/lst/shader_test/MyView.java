package cn.ssdut.lst.shader_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/12/20.
 */
public class MyView extends View {
    public Paint paint;

    //在Layout文件中静态注册View要使用此构造器
    public MyView(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
        paint.setColor(Color.GRAY);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制矩形
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
    }
}
