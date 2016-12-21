package cn.ssdut.lst.listviewteen;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2016/12/21.
 * 自定义了一个动画类,需要重写applyTransfomation()方法
 */

public class MyAnimation extends Animation {
    private float centerX;
    private float centerY;
    private int duration;
    private Camera camera = new Camera();

    public MyAnimation(float x, float y, int dur) {
        centerX =x;
        centerY = y;
        duration = dur;
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(duration);
        //设置动画结束后效果保留
        setFillAfter(true);
        setInterpolator(new LinearInterpolator());
    }

    //重写applyTransformation方法
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        camera.save();
        //根据InterpolatedTime时间来控制x,y,z上的偏移
        camera.translate(100.0f - 100.0f * interpolatedTime, 150.0f * interpolatedTime - 150, 80.0f - 80f * interpolatedTime);
        //设置根据InterpolatedTime时间在y轴上旋转的角度
        camera.rotateY(360 * (interpolatedTime));
        camera.rotateX(360 * interpolatedTime);
        //获取Transformation的matrix对象
        Matrix matrix = t.getMatrix();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        camera.restore();
    }
}
