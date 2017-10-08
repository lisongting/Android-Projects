package cn.iscas.xlab.mainpagers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.allenliu.badgeview.BadgeFactory;
import com.allenliu.badgeview.BadgeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongting on 2017/10/8.
 * 使用BottomNavigationView的好处是底部的tab按钮在每次选中的时候都会变大一下
 */

public class MainPageActivity3 extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragments;
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mainpage3);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragments = new ArrayList<>();

        fragments.add(SimpleFragment.getInstance("航班"));
        fragments.add(SimpleFragment.getInstance("车次"));
        fragments.add(SimpleFragment.getInstance("我的"));

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.plane);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.train);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.user);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(fragmentPagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.plane:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.train:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.user:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        //想给下方的tab添加badge效果，但是没有达到想要的效果。
        // 可能是由于下方是一个menu而不是直接的view
        View v = bottomNavigationView.getChildAt(1);
        BadgeFactory.create(this)
                .setTextColor(Color.WHITE)
                .setWidthAndHeight(25,25)
                .setBadgeBackground(Color.RED)
                .setTextSize(10)
                .setBadgeGravity(Gravity.TOP|Gravity.RIGHT)
                .setBadgeCount(18)
                .setShape(BadgeView.SHAPE_CIRCLE)
                .setSpace(10,10)
                .bind(v);
    }
}
