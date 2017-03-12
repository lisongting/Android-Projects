package cn.ssdut.lst.viewpagerindicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/3/11.
 */

public class TabAdapter extends FragmentPagerAdapter {
    public static String[] titles = new String[]{"推荐","热点","头条","科技","财经"};
    public TabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        TabFragment  fragment = new TabFragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
