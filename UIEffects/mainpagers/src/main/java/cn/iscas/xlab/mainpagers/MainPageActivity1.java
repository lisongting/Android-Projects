package cn.iscas.xlab.mainpagers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lisongting on 2017/10/6.
 */

public class MainPageActivity1 extends AppCompatActivity{

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mainpage1);

        initView();
        initEvents();

        fragmentPagerAdapter = new FragmentPagerAdapter() {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        }
    }

    private void initEvents() {

    }

    private void initView() {

    }





}
