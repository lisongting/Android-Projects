package cn.ssdut.lst.path_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(MainActivity.this));
    }

    class MyView extends View {
        float phase;
        PathEffect[] effects = new PathEffect[7];
        int[] colors;
        private Paint paint;
        Path path;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);//只绘制图形轮廓
            paint.setStrokeWidth(4);
            path = new Path();
            path.moveTo(0, 0);
            for (int i = 0; i < 40; i++) {
                //生成40个点，随机生成y坐标，将他们连成一条path
                path.lineTo(i * 20, (float) Math.ra1dom() * 60);
            }
            colors = new int[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};
            Toast.makeText(context, "phase值是："+phase, Toast.LENGTH_SHORT).show();
        }


        protected void onDraw(Canvas canvas) {
            //背景设为白色
            canvas.drawColor(Color.WHITE);
            effects[0] = null;
            effects[1] = new CornerPathEffect(10);
            effects[2] = new DiscretePathEffect(3.0f, 5.0f);
            effects[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, phase);
            Path p = new Path();
            p.addRect(0, 0, 8, 8, Path.Direction.CCW);
            effects[4] = new PathDashPathEffect(p, 12, phase, PathDashPathEffect.Style.ROTATE);
            effects[5] = new ComposePathEffect(effects[4], effects[3]);//一个作为内部效果，一个作为外部效果
            effects[6] = new SumPathEffect(effects[4], effects[3]);
            canvas.translate(8, 8);//将画布移到(8,8)处开始绘制
            //使用7种不同的路径效果，7种不同的颜色来绘制图形
            for (int i = 0; i < 7; i++) {
                paint.setPathEffect(effects[i]);
                paint.setColor(colors[i]);
                canvas.drawPath(path, paint);
                canvas.translate(0, 60);
            }
            //改变phase值，形成动画效果
            invalidate();

        }
    }
}
