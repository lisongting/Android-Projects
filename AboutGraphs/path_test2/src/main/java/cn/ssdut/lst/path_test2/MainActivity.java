package cn.ssdut.lst.path_test2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 沿着路径绘制文本
 * 2016.12.15
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));
    }
    class TextView extends View {
        final String DRAW_STR="黄河之水天上来，奔流到海不复回";
        Path[] paths = new Path[3];
        Paint paint;
        public TextView(Context context) {
            super(context);
            paths[0] = new Path();
            paths[0].moveTo(0, 0);
            for(int i=0;i<=20;i++) {
                //生成20个点，随机生成他们的y坐标，将他们连成一条path
                paths[0].lineTo(i * 30, (float) Math.random() * 30);
            }
            RectF rectF = new RectF(0, 0, 600, 360);//建立RectangleField
            paths[1] = new Path();
            //添加一个闭合的椭圆轮廓，逆时针方向
            paths[1].addOval(rectF, Path.Direction.CCW);
            paths[2] = new Path();
            paths[2].addArc(rectF,60,180);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.CYAN);//青色？
            paint.setStrokeWidth(1);
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas.translate(40, 40);

            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(20);
            paint.setStyle(Paint.Style.STROKE);//只有描边
            canvas.drawPath(paths[0],paint);
            paint.setTextSize(40);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[0], -8, 20, paint);

            //对canvas进行坐标变换，画布下移60，绘制第二条
            canvas.translate(0, 60);
            //绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawTextOnPath(DRAW_STR, paths[1], -20, 20, paint);

            //画布再次下移,绘制第三条
            canvas.translate(0, 360);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[2], paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR,paths[2],-10,20,paint);
        }
    }
}
