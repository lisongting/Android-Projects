package cn.iscas.xlab.mainpagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongting on 2017/10/7.
 */

public class MainPageActivity2 extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerIndicatorLayout indicatorLayout;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<SimpleFragment> fragmentList = new ArrayList<>();

    private String[] titles = new String[]{"热点","头条","财经","科技","视频","趣图","影评","小说","段子"};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mainpage2);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        indicatorLayout = (ViewPagerIndicatorLayout) findViewById(R.id.indicator);

        getSupportActionBar().hide();


        for (String title : titles) {
            fragmentList.add(SimpleFragment.getInstance(title));
        }

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);

        indicatorLayout.setViewPager(viewPager,0);
    }

}
