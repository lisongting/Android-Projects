package cn.lst.jolly.timeline;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.lst.jolly.R;

/**
 * Created by lisongting on 2018/1/16.
 */

public class TimelineFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int pageCount = 3;
    private String[] titles;
    private ZhihuDailyFragment zhihuDailyFragment;
    private DoubanMomentFragment doubanMomentFragment;
    private GuokrHandpickFragment guokrHandpickFragment;

    public TimelineFragmentPagerAdapter(FragmentManager fm,
                                        Context context,
                                        ZhihuDailyFragment zhihuDailyFragment,
                                        DoubanMomentFragment doubanMomentFragment,
                                        GuokrHandpickFragment guokrHandpickFragment) {
        super(fm);
        this.zhihuDailyFragment = zhihuDailyFragment;
        this.doubanMomentFragment = doubanMomentFragment;
        this.guokrHandpickFragment = guokrHandpickFragment;
        titles = new String[]{context.getString(R.string.zhihu_daily),
                context.getString(R.string.douban_moment),
                context.getString(R.string.guokr_handpick)};
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return zhihuDailyFragment;
            case 1:
                return doubanMomentFragment;
            case 2:
                return guokrHandpickFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
