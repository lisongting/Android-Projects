package cn.ssdut.lst.uieffects;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by Administrator on 2017/4/2.
 */
//自定义的ViewPager
public class CustomViewPager extends ViewPager {

    private View mLeft;
    private View mRight;
    private float mTrans;
    private float mScale;
    private Map<Integer, View> mChildren = new HashMap<>();

    private static final float MIN_SCALE = 0.5F;

    //初始化Map<Integer,View>对象
    public void setViewForPosition(View v,int pos) {
        mChildren.put(pos, v);
    }

    public void removeFromPosition(int pos) {
        mChildren.remove(pos);
    }


    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {

        super(context,attrs);
    }

    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        Log.i("tag","position:"+position+"  offset:"+offset+"  offsetPixels:"+offsetPixels);
        //由位置获取到应该显示的View
        mLeft = mChildren.get(position);
        mRight = mChildren.get(position + 1);

        //为left和right添加动画效果
        addAnim(mLeft, mRight, offset, offsetPixels);

        super.onPageScrolled(position,offset,offsetPixels);
    }

    private void addAnim(View mLeft, View mRight, float offset, int offsetPixels) {
        if (mRight != null) {
            //从第0页到第一页,offset从0到1
            //mScale也逐渐从Min_Scale到1
            mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;
            //mTrans：从0到宽度变化
            mTrans = -getWidth()-getPageMargin()+offsetPixels;

            mRight.setScaleX(mScale);
            mRight.setScaleY(mScale);
            mRight.setTranslationX(mTrans);
        }
        if (mLeft != null) {
            mLeft.bringToFront();

        }
    }

}
