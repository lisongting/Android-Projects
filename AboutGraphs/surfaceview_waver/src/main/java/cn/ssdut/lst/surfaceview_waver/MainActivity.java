package cn.ssdut.lst.surfaceview_waver;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SurfaceHolder holder;
    private Paint paint;
    final int HEIGHT=320;
    final int WIDTH = 768;
    final int X_OFFSET = 5;
    private int cx = X_OFFSET;
    int centerY = HEIGHT/2;
    Timer timer = new Timer();
    TimerTask task = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SurfaceView surface = (SurfaceView) findViewById(R.id.show);
        holder = surface.getHolder();//surfaceView的绘图是通过holder来完成的
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);

    }

    private void drawBack(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(2);
        canvas.drawLine(X_OFFSET, centerY, WIDTH, centerY, p);
        holder.unlockCanvasAndPost(canvas);
        holder.lockCanvas(new Rect(0, 0, 0, 0));
        holder.unlockCanvasAndPost(canvas);
    }
    public void sin_show(View v) {

    }

    public void cos_show(View view) {

    }
}
