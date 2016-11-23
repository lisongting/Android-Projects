package cn.ssdut.lst.aboutgraphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/15.
 */
public class MyView extends View {
    public MyView(Context context) {

        super(context);
    }

    //重写该方法，进行绘图
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);//把整张画布绘制成白色
        Paint paint = new Paint();//新建画笔
        paint.setAntiAlias(true);//去锯齿
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        int vw = this.getWidth();

        Toast.makeText(getContext(), "View的宽度为：" + vw+"\n屏幕高度为："+this.getHeight()+"像素", Toast.LENGTH_LONG).show();//??
        //画圆形
        canvas.drawCircle(vw/10+10,vw/10+10,vw/10,paint);//(点x坐标，点y坐标,半径，画笔)
        canvas.drawRect(10 , vw / 5 + 20 , vw / 5 + 10
                , vw * 2 / 5 + 20 , paint);
        // 绘制矩形
        canvas.drawRect(10, vw * 2 / 5 + 30, vw / 5 + 10
                , vw / 2 + 30, paint);
        //绘制圆角矩形
        RectF rel = new RectF(10,vw/2+40,vw/5+10,vw*3/5+40);
        canvas.drawRoundRect(rel,15,15,paint);//中间那两个参数是什么意思？
        RectF rell = new RectF(10,vw*3/5+50,10+vw/5,vw*7/10 +50);
        canvas.drawOval(rell, paint);
        Path path1 = new Path();//定义一个Path对象，封闭成一个三角形
        path1.moveTo(10+vw/15,vw*9/10+70);//path的起点
        path1.lineTo(vw/5+10,vw*9/10+60);//往一个点画线
        path1.lineTo(vw/10+10,vw*7/10+60);
        path1.close();//闭合图形
        canvas.drawPath(path1,paint);
        //---------为画笔设置填充风格-----------------
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        //画圆形
        canvas.drawCircle(vw*3/10+20,vw/10+10,vw/10,paint);//(点x坐标，点y坐标,半径，画笔)
        //绘制正方形
        canvas.drawRect(vw / 5 + 20 , vw / 5 + 20
                , vw * 2 / 5 + 20 , vw * 2 / 5 + 20 , paint);
        // 绘制矩形
        canvas.drawRect(vw / 5 + 20, vw * 2 / 5 + 30
                , vw * 2 / 5 + 20 , vw / 2 + 30, paint);
        //绘制圆角矩形
        RectF re2 = new RectF(vw/5+20,vw/2+40,vw*2/5+20,vw*3/5+40);
        canvas.drawRoundRect(re2,15,15,paint);//中间那两个参数是什么意思？
        RectF re21 = new RectF(vw/5+20,50+vw*3/5,vw*2/5 +20,vw*7/10+50);
        canvas.drawOval(re21, paint);
        Path path3 = new Path();
        path3.moveTo(20 + vw / 5, vw * 9 / 10 + 60);
        path3.lineTo(vw * 2 / 5 + 20, vw * 9 / 10 + 60);
        path3.lineTo(vw * 3 / 10 + 20, vw * 7 / 10 + 60);
        path3.close();
        canvas.drawPath(path3,paint);
        //-------为paint设置渐变器------------
        Shader shader = new LinearGradient(0, 0, 40, 60, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, null, Shader.TileMode.REPEAT);
        paint.setShader(shader);
        paint.setShadowLayer(25,20,20,Color.GRAY);//设置阴影
        canvas.drawCircle(vw / 2 + 30, vw / 10 + 10
                , vw / 10, paint);
        // 绘制正方形
        canvas.drawRect(vw * 2 / 5 + 30 , vw / 5 + 20
                , vw * 3 / 5 + 30 , vw * 2 / 5 + 20 , paint);
        // 绘制矩形
        canvas.drawRect(vw * 2 / 5 + 30, vw * 2 / 5 + 30
                , vw * 3 / 5 + 30 , vw / 2 + 30, paint);
        RectF re3 = new RectF(vw * 2/5 + 30, vw / 2 + 40
                , 30 + vw * 3 / 5 ,vw * 3 / 5 + 40);
        // 绘制圆角矩形
        canvas.drawRoundRect(re3, 15, 15, paint);
        RectF re31 = new RectF(30 + vw *2 / 5, vw * 3 / 5 + 50
                , 30 + vw * 3 / 5 ,vw * 7 / 10 + 50);
        // 绘制椭圆
        canvas.drawOval(re31, paint);
        // 定义一个Path对象，封闭成一个三角形
        Path path5 = new Path();
        path5.moveTo(30 + vw * 2/ 5, vw * 9 / 10 + 60);
        path5.lineTo(vw * 3 / 5 + 30, vw * 9 / 10 + 60);
        path5.lineTo(vw / 2 + 30, vw * 7 / 10 + 60);
        path5.close();
        // 根据Path进行绘制，绘制三角形
        canvas.drawPath(path5, paint);
    }
}
