package cn.ssdut.lst.surfaceview_anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        public void resume

}
