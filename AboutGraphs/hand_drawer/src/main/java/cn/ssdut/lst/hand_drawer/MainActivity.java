package cn.ssdut.lst.hand_drawer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EmbossMaskFilter emboss;//浮雕过滤器
    BlurMaskFilter blur;//模糊过滤器
    DrawView drawView;

    @Override
    @TargetApi(17)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout line = new LinearLayout(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //获取创建的宽度和高度
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        //创建一个DrawView,该DrawView的宽高与屏幕相同
        drawView = new DrawView(this, displayMetrics.widthPixels, displayMetrics.heightPixels);
        Toast.makeText(MainActivity.this, "width:"+displayMetrics.widthPixels+";height:"+displayMetrics.heightPixels, Toast.LENGTH_SHORT).show();
        line.addView(drawView);
        setContentView(line);
        //EmbossMaskFilter构造函数
        //EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius)
        //float[]{x,y,z}:光源的位置  ambient:光强度(0,1]  specular：高光系数   blurRadius：模糊半径
        emboss = new EmbossMaskFilter(new float[]{1.5f, 1.5f, 1.5f}, 0.6f, 6, 4.2f);//????
        blur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        //加载对应的菜单布局，并添加到menu中
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {
            case R.id.red:
                drawView.paint.setColor(Color.RED);
                mi.setChecked(true);
                break;
            case R.id.green:
                drawView.paint.setColor(Color.GREEN);
                mi.setChecked(true);
                break;
            case R.id.blue:
                drawView.paint.setColor(Color.BLUE);
                mi.setChecked(true);
                break;
            case R.id.width_1:
                drawView.paint.setStrokeWidth(1);
                break;
            case R.id.width_3:
                drawView.paint.setStrokeWidth(3);
                break;
            case R.id.width_5:
                drawView.paint.setStrokeWidth(5);
                break;
            case R.id.blur:
                drawView.paint.setMaskFilter(blur);
                break;
            case R.id.emboss:
                drawView.paint.setMaskFilter(emboss);
                break;
        }
        return true;
    }
    public class DrawView extends View {
        float preX,preY;
        private Path path;
        public Paint paint = null;
        //定义内存中的一张图片，该图片作为缓冲区
        Bitmap cacheBitmap = null;
        //bitmap上的Canvas对象
        Canvas cacheCanvas = null;

        public DrawView(Context context, int width, int height) {
            super(context);
            //ARGB_8888   每个像素4字节，每个通道以8位二进制数表示
            cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            cacheCanvas = new Canvas();
            path = new Path();
            //设置cacheCanvas将会绘制到内存中的cacheBitmap中
            cacheCanvas.setBitmap(cacheBitmap);
            paint = new Paint(Paint.DITHER_FLAG);//在图像块传输的时候允许抖动
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            paint.setAntiAlias(true);
            paint.setDither(true);
        }

        public boolean onTouchEvent(MotionEvent event) {
            //获取事件发生的位置
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);//设置path的起点为(x,y)
                    preX = x;
                    preY = y;
                    Log.d("tag","-------ActionDown----");
                    break;
                //ACTION_MOVE的处罚频率很高，只要手指在屏幕上时就会一直触发ACTION_MOVE
                case MotionEvent.ACTION_MOVE:
                    path.quadTo(preX,preY,x,y);//从(preX,preY)到(x,y)绘制二次贝塞尔曲线
                    preX = x;
                    preY = y;
                    Log.d("tag","-------ActionMove----");
                    break;
                case MotionEvent.ACTION_UP:
                    cacheCanvas.drawPath(path, paint);
                    path.reset();
                    Log.d("tag","-------ActionUp----");
                    break;
            }
            invalidate();
            //return true 表示已经处理该事件
            return true;
        }
        public void onDraw(Canvas canvas){
            Paint bmpPaint = new Paint();
            //将cacheBitmap绘制到该View组件上
            canvas.drawBitmap(cacheBitmap, 0, 0, bmpPaint);
            //沿着path绘制
            canvas.drawPath(path,paint);

        }
    }
}
