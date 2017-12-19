package cn.lst.robotdisplay;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by lisongting on 2017/12/19.
 */

public class MyGLSurfaceView extends GLSurfaceView implements GestureDetector.OnGestureListener{
    private float mPreviousX;
    private float mPreviousY;
    private GLRenderer renderer;
    private float angleX, angleY;
    private GestureDetector gestureDetector;

    public MyGLSurfaceView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, this);
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        float scaleFactor = 0.4F;
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float dx = x - mPreviousX;
//                float dy = y - mPreviousY;
//                if (y > v.getHeight() / 2) {
//                    dx = dx * -1;
//                }
//                if (x < v.getWidth() / 2) {
//                    dy = dy * -1;
//                }
//                renderer.setAngle(renderer.getAngle()+(dx+dy)*scaleFactor);
//                requestRender();
//            default:
//                break;
//        }
//        mPreviousX = x;
//        mPreviousY = y;
//        return true;
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        log("onTouchEvent");
        boolean b = gestureDetector.onTouchEvent(event);
        return b;
    }



    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        log("gestureDetector -- onFling velocityX:" + velocityX + "  velocityY:" + velocityY);
//        float ROTATE_FACTOR = 80;
//
//        velocityX = velocityX > 2000 ? 2000 : velocityX;
//        velocityX = velocityX < -2000 ? -2000 : velocityX;
//        velocityY = velocityY > 2000 ? 2000 : velocityY;
//        velocityY = velocityY < -2000 ? -2000 : velocityY;
//        // 根据横向上的速度计算沿Y轴旋转的角度
//        angleY += velocityX * ROTATE_FACTOR / 4000;
//        // 根据纵向上的速度计算沿X轴旋转的角度
//        angleX += velocityY * ROTATE_FACTOR / 4000;
//        renderer.setAngle(angleX, angleY);
        return true;
    }
    
    @Override
    public void setRenderer(Renderer renderer) {
        this.renderer = (GLRenderer) renderer;
        super.setRenderer(renderer);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        log("gestureDetector -- onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        log("gestureDetector -- onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        log("gestureDetector -- onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        log("gestureDetector -- onScroll  "+"distanceX:" + distanceX + " distanceY:" + distanceY);
        float ROTATE_FACTOR = 80;

//        velocityX = velocityX > 2000 ? 2000 : velocityX;
//        velocityX = velocityX < -2000 ? -2000 : velocityX;
//        velocityY = velocityY > 2000 ? 2000 : velocityY;
//        velocityY = velocityY < -2000 ? -2000 : velocityY;
//        // 根据横向上的速度计算沿Y轴旋转的角度
//        angleY += velocityX * ROTATE_FACTOR / 4000;
//        // 根据纵向上的速度计算沿X轴旋转的角度
//        angleX += velocityY * ROTATE_FACTOR / 4000;


        renderer.setAngle(renderer.getAngleX()-distanceX,renderer.getAngleY()- distanceY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        log("gestureDetector -- onLongPress");
    }

    private void log(String s) {
        Log.i("tag", s);
    }

   
}
