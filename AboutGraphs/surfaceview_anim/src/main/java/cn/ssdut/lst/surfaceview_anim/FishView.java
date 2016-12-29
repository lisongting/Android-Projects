package cn.ssdut.lst.surfaceview_anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Administrator on 2016/12/22.
 */
public class FishView extends SurfaceView
    implements SurfaceHolder.Callback{
    private SurfaceHolder holder;
    private UpdateViewThread updateThread;
    private boolean hasSurface;
    private Bitmap back;
    private Bitmap[] fishs;
    private int fishIndex = 0;//鱼图片的索引
    private float fishX = 778;
    private float fishY = 500;
    private float fishSpeed = 6;
    private int fishAngle = new Random().nextInt(60);//鱼游动的角度
    Matrix matrix = new Matrix();

    public FishView(Context context, AttributeSet set) {
            super(context, set);
            holder = getHolder();
            holder.addCallback(this);
            hasSurface = false;
            //背景
            back = BitmapFactory.decodeResource(context.getResources(), R.drawable.fishbg);
            fishs = new Bitmap[10];
            for(int i=0;i<10;i++) {
                    try {
                            //获取到鱼的id,并初始化鱼图片数组
                            int fishId = (Integer) R.drawable.class.getField("fish" + i).get(null);
                            fishs[i] = BitmapFactory.decodeResource(context.getResources(), fishId);
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
            }
    }

    public void resume() {
            //创建和启动图像更新线程
        if (updateThread == null) {
            updateThread = new UpdateViewThread();
            if (hasSurface == true) {
                updateThread.start();
            }
        }
    }
    public void pause(){
        //停止图像更新线程
        if (updateThread != null) {
            updateThread.requestExitAndWait();
            updateThread = null;
        }

    }
    //surface被创建时回掉该方法
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        hasSurface = true;
        resume();
    }
    //surfaceView将要被销毁时回掉该方法
    public void surfaceDestroyed(SurfaceHolder holder){
        hasSurface = false;
        pause();
    }
    //当SurfaceView发生改变时调用该方法
    public void surfaceChanged(SurfaceHolder holder,int format,int w,int h){
        if (updateThread != null) {
            updateThread.onWindowResize(w,h);
        }
    }
    class UpdateViewThread extends Thread{
        private boolean done;

        UpdateViewThread() {
            super();
            done = false;
        }
        public void run(){
            SurfaceHolder surfaceHolder = holder;
            //重复绘图循环，直到线程停止
            while (!done) {
                //锁定surfaceView，并返回到要绘图的Canvas
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawBitmap(back, 0, 0, null);
                if (fishX < 0) {
                    fishX = 778;
                    fishY = 500;
                }
                if(fishY<0){
                    fishX = 778;
                    fishY = 500;
                    fishAngle = new Random().nextInt(60);
                }
                //使用matrix来控制鱼的旋转角度
                matrix.reset();
                matrix.setRotate(fishAngle);
                matrix.postTranslate(fishX -= fishSpeed * Math.cos(Math.toRadians(fishAngle)),
                        fishY -= fishSpeed * Math.sin(Math.toRadians(fishAngle)));
                canvas.drawBitmap(fishs[fishIndex++ % fishs.length], matrix, null);
                //解锁canvas，并渲染当前图像
                surfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(60);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void requestExitAndWait() {
            //把这个线程标记为完成，并合并到主线程中
            done = true;
            try {
                join();//合并
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onWindowResize(int w, int h) {

        }
    }
}
