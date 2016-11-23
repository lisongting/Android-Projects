package com.test.administrator.gesturetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    GestureDetector detector;
    ViewFlipper flipper;
    Animation[]animations = new Animation[4];
    final int  FLIP_DISTANCE=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detector = new GestureDetector(this,this);
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        flipper.addView(addImageView(R.drawable.ajax));
        flipper.addView(addImageView(R.drawable.javaee));
        flipper.addView(addImageView(R.drawable.java));
        flipper.addView(addImageView(R.drawable.android));
        flipper.addView(addImageView(R.drawable.html));
        flipper.addView(addImageView(R.drawable.swift));
        //把动画的xml文件加载到动画数组中去
        animations[0] = AnimationUtils.loadAnimation(this,R.anim.left_in);
        animations[1] = AnimationUtils.loadAnimation(this,R.anim.left_out);
        animations[2] = AnimationUtils.loadAnimation(this,R.anim.right_in);
        animations[3] = AnimationUtils.loadAnimation(this,R.anim.right_out);
    }
    private View addImageView(int resID){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resID);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }
    public boolean onTouchEvent(MotionEvent me){
        return detector.onTouchEvent(me);
    }
    //下面6个是OnGestureListenener接口里包含的方法
    public boolean onFling(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){
        //如果第一个触点事件的x坐标与第二个触点事件的x坐标差距超过FLIP_DISTANCE
        //手势从右向左划
        if(e1.getX()-e2.getX()>FLIP_DISTANCE){
            flipper.setInAnimation(animations[0]);
            flipper.setOutAnimation(animations[1]);
            flipper.showPrevious();
            return true;
        }else if(e2.getX()-e1.getX()>FLIP_DISTANCE){
            flipper.setInAnimation(animations[2]);
            flipper.setOutAnimation(animations[3]);
            flipper.showNext();
            return true;
        } 
        return false;
    }

    public boolean onDown(MotionEvent arg0){
        return false;
    }
    public void onLongPress(MotionEvent e){
    }
    public boolean onScroll(MotionEvent e1,MotionEvent e2,float distanceX,float distanceY){
        return false;

    }
    public void onShowPress(MotionEvent e){
    }
    public boolean onSingleTapUp(MotionEvent e){
        return false;
    }
}
