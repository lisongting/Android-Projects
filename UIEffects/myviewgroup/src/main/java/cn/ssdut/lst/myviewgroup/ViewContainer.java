package cn.ssdut.lst.myviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 自定义ViewGroup
 * Created by Administrator on 2017/3/31.
 */

public class ViewContainer extends ViewGroup {


    public ViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new MarginLayoutParams(getContext(), attributeSet);
    }
    //预先给四个子view的参数进行测量
    @Override
    protected void onMeasure(int widthMeaseSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeaseSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeaseSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        Toast.makeText(getContext(), "sizeWidth(px):"+sizeWidth, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "sizeHeight(px):"+sizeHeight, Toast.LENGTH_SHORT).show();

        //计算出所有的子View的宽和高
        measureChildren(widthMeaseSpec, heightMeasureSpec);

        int width = 0;
        int height = 0 ;
        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        //用于计算左边两个childView的高度
        int lHeight = 0;
        //用于计算右边两个childView的高度
        int rHeight = 0;

        //上面部分的宽
        int tWidth = 0;
        //下面部分的宽
        int bWidth = 0;
        Toast.makeText(getContext(), "子View的数量："+cCount, Toast.LENGTH_SHORT).show();
        for(int i=0;i<cCount;i++) {
            View child = getChildAt(i);
            cWidth = child.getWidth();
            cHeight = child.getHeight();
            cParams = (MarginLayoutParams) child.getLayoutParams();
            //上面两个childView
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            //下面两个childView
            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.bottomMargin + cParams.topMargin;
            }

            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.bottomMargin + cParams.topMargin;
            }

        }
        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        //如果是wrap_content，则设置为计算好的值
        //否则，设置为父容器中定义的值
        setMeasuredDimension((widthMode==MeasureSpec.EXACTLY)?sizeWidth : width,
                (heightMode==MeasureSpec.EXACTLY)?sizeHeight:height);
     }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        for(int i=0;i<cCount;i++) {
            View child = getChildAt(i);
            cWidth = child.getMeasuredWidth();
            cHeight = child.getMeasuredHeight();
            cParams = (MarginLayoutParams) child.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;
            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
            }
            cr = cl + cWidth;
            cb = cHeight +ct;
            //调用child的layout函数，分别将左上右下的坐标传进去
            child.layout(cl,ct,cr,cb);
        }

    }
}
