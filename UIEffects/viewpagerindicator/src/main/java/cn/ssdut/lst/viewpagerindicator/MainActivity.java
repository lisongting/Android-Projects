package cn.ssdut.lst.viewpagerindicator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ViewPager和tabPagerIndicator来模仿类似新闻客户端顶部标题滑动的页面
 */
public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private TabPageIndicator tabPageIndicator;
    private List<Fragment> fragments = new ArrayList<>();
    TabAdapter  adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

    }
    public void initView(){
        //下面都是为了构造ViewPager
        viewPager = (ViewPager) findViewById(R.id.id_viewPager);
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        adapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //将ViewPager设置到TabPageIndicator中
        //将tabPageIndicator设置一个ViewPager
        tabPageIndicator.setViewPager(viewPager,0);

    }
}
