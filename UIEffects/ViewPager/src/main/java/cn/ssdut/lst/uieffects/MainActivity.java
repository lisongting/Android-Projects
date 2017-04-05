package cn.ssdut.lst.uieffects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private LinearLayout mTabApple;
    private LinearLayout mTabBanana;
    private LinearLayout mTabMango;
    private LinearLayout mTabOrange;
    private ImageButton mAppleImg;
    private ImageButton mBananaImg;
    private ImageButton mMangoImg;
    private ImageButton mOrangeImg;
    //适配器
    private PagerAdapter mAdapter;
    //适配器的创建需要一个View集合
    private List<View> mViews = new ArrayList<>();
    private Button bt1,bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initEvents();
    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mTabApple = (LinearLayout) findViewById(R.id.id_tab1);
        mTabBanana = (LinearLayout) findViewById(R.id.id_tab2);
        mTabMango = (LinearLayout) findViewById(R.id.id_tab3);
        mTabOrange = (LinearLayout) findViewById(R.id.id_tab4);

        mAppleImg = (ImageButton) findViewById(R.id.id_ib1);
        mBananaImg = (ImageButton) findViewById(R.id.id_ib2);
        mMangoImg = (ImageButton) findViewById(R.id.id_ib3);
        mOrangeImg = (ImageButton) findViewById(R.id.id_ib4);
        bt1 = (Button) findViewById(R.id.id_bt1);
        bt2 = (Button) findViewById(R.id.id_bt2);

        //通过使用Inflater将Layout中的的布局转换为View
        LayoutInflater inflater = LayoutInflater.from(this);
        View tab01 = inflater.inflate(R.layout.tab01, null);
        View tab02 = inflater.inflate(R.layout.tab02, null);
        View tab03 =  inflater.inflate(R.layout.tab03, null);
        View tab04 = inflater.inflate(R.layout.tab04, null);
        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);

        //初始化mAdapter
        mAdapter = new PagerAdapter() {

            //初始化PagerAdapter还要重写以下方法
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        //为ViewPager设置Adapter
        mViewPager.setAdapter(mAdapter);

    }

    private void initEvents() {
        mAppleImg.setOnClickListener(this);
        mBananaImg.setOnClickListener(this);
        mOrangeImg.setOnClickListener(this);
        mMangoImg.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                resetImage();
                switch (currentItem) {
                    case 0:
                        mAppleImg.setImageResource(R.drawable.clicked);
                        break;
                    case 1:
                        mBananaImg.setImageResource(R.drawable.clicked);
                        break;
                    case 2:
                        mMangoImg.setImageResource(R.drawable.clicked);
                        break;
                    case 3:
                        mOrangeImg.setImageResource(R.drawable.clicked);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        resetImage();
        switch (v.getId()) {
            case R.id.id_ib1:
                mViewPager.setCurrentItem(0);
                mAppleImg.setImageResource(R.drawable.clicked);
                break;
            case R.id.id_ib2:
                mViewPager.setCurrentItem(1);
                mBananaImg.setImageResource(R.drawable.clicked);
                break;
            case R.id.id_ib3:
                mViewPager.setCurrentItem(2);
                mMangoImg.setImageResource(R.drawable.clicked);
                break;
            case R.id.id_ib4:
                mViewPager.setCurrentItem(3);
                mOrangeImg.setImageResource(R.drawable.clicked);
                break;
            case R.id.id_bt1:
                //启动 引导页
                Intent t = new Intent(this, GuidePageActivity.class);
                startActivity(t);
                break;
            case R.id.id_bt2:
                Intent t2 = new Intent(this, CustomViewPagerActivity.class);
                startActivity(t2);
                break;
        }
    }

    private void resetImage() {
        mAppleImg.setImageResource(R.drawable.apple);
        mBananaImg.setImageResource(R.drawable.banana);
        mMangoImg.setImageResource(R.drawable.mango);
        mOrangeImg.setImageResource(R.drawable.orange);

    }
}
