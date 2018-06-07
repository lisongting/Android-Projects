package com.lst.wanandroid.widget.behavior;


import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;

//FloatingActionBar的行为控制器
//使用时要指定FloatingActionBar的app:layout_behavior属性，令其指向一String，String为该行为指示器的全限定类名
public class ScaleDownShowBehavior extends FloatingActionButton.Behavior{

    //是否正在动画
    private boolean isAnimating = false;
    //是否在显示
    private boolean isShow = true;

    public ScaleDownShowBehavior(Context context, AttributeSet attributeSet) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child,
                                       View directTargetChild,
                                       View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child,directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //手指上滑，隐藏FAB
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimating && isShow) {
            AnimatorUtil.translateHide(child, new StateListener() {
                @Override
                public void onAnimationStart(View view) {
                    super.onAnimationStart(view);
                    isShow = false;
                }
            });
        } else if(dyConsumed<0||dyUnconsumed<0&&!isAnimating&&!isShow){
            AnimatorUtil.translateShow(child,new StateListener(){
                public void onAnimatorStart(View view) {
                    super.onAnimationStart(view);
                    isShow = true;
                }
            });
        }

    }

    class StateListener implements ViewPropertyAnimatorListener {
        @Override
        public void onAnimationStart(View view) {
            isAnimating = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimating = false;
        }

        @Override
        public void onAnimationCancel(View view) {
            isAnimating = false;
        }
    }

}
