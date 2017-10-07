package cn.iscas.xlab.mainpagers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lisongting on 2017/10/7.
 * ViewPager+三角形指示器+滑动切换+点击切换
 * 仿新闻头部效果
 */

public class ViewPagerIndicatorLayout extends LinearLayout {

    private Paint paint;
    private Path path;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private int mInitTranslationX;
    private int mTranslationX;
    private int mTabVisibleCount;
    private ViewPager viewPager;
    private PageOnChangeListener viewPagerListener;

    private static final float RADIO_TRIANGLE_WIDTH = 1/6F;
    //显示在界面上的默认的tab数量为4个
    private static final int DEFAULT_TAB_COUNT = 4;

    //未选中时的字体颜色
    public static final int COLOR_TEXT_NORMAL = R.color.primary_text;
    //选中时的字体颜色
    public static final int COLOR_TEXT_HIGHLIGHT = R.color.colorAccent;


    public ViewPagerIndicatorLayout(Context context) {
        this(context,null);
    }

    public ViewPagerIndicatorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicatorLayout);
        mTabVisibleCount = array.getInt(R.styleable.ViewPagerIndicatorLayout_visible_tab_count, DEFAULT_TAB_COUNT);



        array.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new CornerPathEffect(3));


    }

    //当控件的宽高发生变化时回调该方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //w是整个Indicator的宽度，h是高度，除以四是因为有四个tab。
        //意思就是让三角形的边长设置为每个tab的六分之一长
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);

        //mInitTranslationX是指三角形左下角的点距离0点的偏移量
        mInitTranslationX  = w/mTabVisibleCount/2 - mTriangleWidth/2;

        initTranslation();
    }

    private void initTranslation() {
        //正三角形的路径
        path = new Path();
        path.moveTo(0,0);
        path.lineTo(mTriangleWidth,0);
        path.lineTo(mTriangleWidth/2, (float) (-mTriangleWidth*Math.sin(Math.PI/3)));
        path.close();

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //注意：这里如果不调用super.dispatchDraw()，则会导致上一级的View无法显示，(每个tab的标题就不会显示)
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mInitTranslationX+mTranslationX,getHeight());
        canvas.drawPath(path,paint);

        canvas.restore();
    }

    //指示器跟随手指进行滚动
    //position是当前页面的id,offset是从0到1的页面偏移相对量
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        mTranslationX = (int) ((position+offset)*tabWidth);
        if (position >= (mTabVisibleCount - 2) && offset > 0 && getChildCount() > mTabVisibleCount) {
            this.scrollTo((position-(mTabVisibleCount-2))*tabWidth+(int)(tabWidth*offset),0);
        }

        invalidate();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for(int i=0;i<childCount;i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight=0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);

        }
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrix = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrix);
        return metrix.widthPixels;
    }

    //这里单独设置一个接口是因为外部可能还会给ViewPager设置监听器，从而将两个Listener共存而不互相影响
    public interface PageOnChangeListener{
        void onPageScroll(int position,float positionOffset,int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);

    }

    public void setOnPageChangeListener(PageOnChangeListener listener){
        this.viewPagerListener = listener;
    }

    //直接可以从外部与ViewPager相互关联
    public void setViewPager(ViewPager viewPager,int startPos){
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position,positionOffset);
                if (viewPagerListener != null) {
                    viewPagerListener.onPageScroll(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (viewPagerListener != null) {
                    viewPagerListener.onPageSelected(position);
                }
                highLightText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (viewPagerListener != null) {
                    viewPagerListener.onPageScrollStateChanged(state);
                }
            }
        });
        viewPager.setCurrentItem(startPos);
        highLightText(startPos);

        setTabClickListener();
    }

    //设置TextView的高亮颜色
    private void highLightText(int pos) {
        resetTextViewColor();
        View child = getChildAt(pos);
        if (child instanceof TextView) {
            ((TextView) child).setTextColor(getResources().getColor(COLOR_TEXT_HIGHLIGHT));
        }
    }

    private void resetTextViewColor(){
        for(int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextColor(getResources().getColor(COLOR_TEXT_NORMAL));
            }
        }
    }

    //设置tab的点击事件
    private void setTabClickListener() {
        for(int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            final int j = i;
            if (child instanceof TextView) {
                child.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(j);
                        highLightText(j);
                    }
                });
            }
        }
    }
}
