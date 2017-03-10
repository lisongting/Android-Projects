package cn.ssdut.lst.fragmenttab;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * 2017.3.10
 * 用Fragment实现点击按钮的界面切换效果
 * 如果要加入滑动切换效果，则要配合ViewPager和FragmentPagerAdapter来使用
 * FragmentPagerAdapter与PagerAdapter使用类似，只是PagerAdapter存放的是View而
 * FragmentPagerAdapter存放的是Fragment
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private LinearLayout mTabApple;
    private LinearLayout mTabBanana;
    private LinearLayout mTabMango;
    private LinearLayout mTabOrange;
    private ImageButton mAppleImg;
    private ImageButton mBananaImg;
    private ImageButton mMangoImg;
    private ImageButton mOrangeImg;
    private FrameLayout frameLayout;
    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initEvents();
        setSelect(0);
    }
    private void initView(){
        mTabApple = (LinearLayout) findViewById(R.id.id_tab1);
        mTabBanana = (LinearLayout) findViewById(R.id.id_tab2);
        mTabMango = (LinearLayout) findViewById(R.id.id_tab3);
        mTabOrange = (LinearLayout) findViewById(R.id.id_tab4);

        mAppleImg = (ImageButton) findViewById(R.id.id_ib1);
        mBananaImg = (ImageButton) findViewById(R.id.id_ib2);
        mMangoImg = (ImageButton) findViewById(R.id.id_ib3);
        mOrangeImg = (ImageButton) findViewById(R.id.id_ib4);
        frameLayout = (FrameLayout) findViewById(R.id.id_framelayout);
        //通过Layout
        mTab01 = new AppleFragment();
        mTab02 = new BananaFragment();
        mTab03 = new MangoFragment();
        mTab04 = new OrangeFragment();


    }
    public void  initEvents(){
        mAppleImg.setOnClickListener(this);
        mBananaImg.setOnClickListener(this);
        mOrangeImg.setOnClickListener(this);
        mMangoImg.setOnClickListener(this);
    }

    //当选中时某个图标时，用西瓜来标识选中状态
    @Override
    public void onClick(View v) {
        resetImage();
        switch (v.getId()) {
            case R.id.id_ib1:
                setSelect(0);
                break;
            case R.id.id_ib2:
                setSelect(1);
                break;
            case R.id.id_ib3:
                setSelect(2);
                break;
            case R.id.id_ib4:
                setSelect(3);
                break;
        }
    }

    //切换图标到默认的图片
    private void resetImage() {
        mAppleImg.setImageResource(R.drawable.apple);
        mBananaImg.setImageResource(R.drawable.banana);
        mMangoImg.setImageResource(R.drawable.mango);
        mOrangeImg.setImageResource(R.drawable.orange);

    }

    private void setSelect(int i) {
        //把图片设置为选中状态(西瓜图片)
        //设置内容区域
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        //把其他不相关的所有Fragment都隐藏
        switch (i) {
            case 0:
                mAppleImg.setImageResource(R.drawable.clicked);
                transaction.replace(R.id.id_framelayout,mTab01);
                break;
            case 1:
                mBananaImg.setImageResource(R.drawable.clicked);
                transaction.replace(R.id.id_framelayout,mTab02);
                break;
            case 2:
                mMangoImg.setImageResource(R.drawable.clicked);
                transaction.replace(R.id.id_framelayout,mTab03);
                break;
            case 3:
                mOrangeImg.setImageResource(R.drawable.clicked);
                transaction.replace(R.id.id_framelayout,mTab04);
                break;
        }
        transaction.commit();
    }

}
