package cn.ssdut.lst.easyreader.homepage;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

/**
 * Created by lisongting on 2017/4/18.
 */

public class MainFragment extends Fragment {
    private Context context;
    private MainPagerAdapter adapter;

    private TabLayout tabLayout;
    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private DoubanMomentFragment doubanMomentFragment;

}
