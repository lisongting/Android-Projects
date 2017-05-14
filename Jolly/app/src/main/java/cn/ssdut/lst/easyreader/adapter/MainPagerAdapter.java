package cn.ssdut.lst.easyreader.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.homepage.DoubanMomentFragment;
import cn.ssdut.lst.easyreader.homepage.GuokrFragment;
import cn.ssdut.lst.easyreader.homepage.ZhihuDailyFragment;

/**
 * Created by lisongting on 2017/4/19.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private final Context context;
    private GuokrFragment guokrFragment;
    private ZhihuDailyFragment zhihuDailyFragment;
    private DoubanMomentFragment doubanMomentFragment;

    public GuokrFragment getGuokrFragment() {
        return guokrFragment;
    }

    public ZhihuDailyFragment getZhihuFragment() {
        return zhihuDailyFragment;
    }

    public DoubanMomentFragment getDoubanFragment() {
        return doubanMomentFragment;
    }

    public MainPagerAdapter(FragmentManager fm,
                            Context context,
                            ZhihuDailyFragment zhihuDailyFragment,
                            GuokrFragment guokrFragment,
                            DoubanMomentFragment doubanMomentFragment
                            ) {
        super(fm);
        this.context = context;
        this.zhihuDailyFragment = zhihuDailyFragment;
        this.doubanMomentFragment = doubanMomentFragment;
        this.guokrFragment = guokrFragment;

        titles = new String[]{
                context.getResources().getString(R.string.zhihu_daily),
                context.getResources().getString(R.string.guokr_handpick),
                context.getResources().getString(R.string.douban_moment)
        };

    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return zhihuDailyFragment;
            case 1:
                return guokrFragment;
            case 2:
                return doubanMomentFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    public CharSequence getPageTitle(int pos) {
        return titles[pos];
    }
}
