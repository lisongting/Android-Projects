package cn.lst.jolly.timeline;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lst.jolly.R;
import cn.lst.jolly.data.source.local.DoubanMomentNewsLocalDataSource;
import cn.lst.jolly.data.source.local.GuokrHandpickNewsLocalDataSource;
import cn.lst.jolly.data.source.local.ZhihuDailyNewsLocalDataSource;
import cn.lst.jolly.data.source.remote.DoubanMomentNewsRemoteDataSource;
import cn.lst.jolly.data.source.remote.GuokrHandpickNewsRemoteDataSource;
import cn.lst.jolly.data.source.remote.ZhihuDailyNewsRemoteDataSource;
import cn.lst.jolly.data.source.repository.DoubanMomentNewsRepository;
import cn.lst.jolly.data.source.repository.GuokrHandpickNewsRepository;
import cn.lst.jolly.data.source.repository.ZhihuDailyNewsRepository;

/**
 * Created by lisongting on 2018/1/2.
 */

public class TimelineFragment extends Fragment {
    private FloatingActionButton mFab;
    private TabLayout mTabLayout;
    private ZhihuDailyFragment zhihuDailyFragment;
    private DoubanMomentFragment doubanMomentFragment;
    private GuokrHandpickFragment guokrHandpickFragment;

    public TimelineFragment(){}

    public static TimelineFragment newInstance(){
        return new TimelineFragment();
    }

    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        if (savedInstaceState != null) {
            FragmentManager fm = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) fm.getFragment(savedInstaceState, ZhihuDailyFragment.class.getSimpleName());
            doubanMomentFragment = (DoubanMomentFragment) fm.getFragment(savedInstaceState, DoubanMomentFragment.class.getSimpleName());
            guokrHandpickFragment = (GuokrHandpickFragment) fm.getFragment(savedInstaceState, GuokrHandpickFragment.class.getSimpleName());
        } else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            guokrHandpickFragment = GuokrHandpickFragment.newInstance();
            doubanMomentFragment = DoubanMomentFragment.newInstance();
        }
        new DoubanMomentPresenter(doubanMomentFragment, DoubanMomentNewsRepository.getInstance(
                DoubanMomentNewsRemoteDataSource.getInstance(),
                DoubanMomentNewsLocalDataSource.getInstance(getContext())
        ));

        new ZhihuDailyPresenter(zhihuDailyFragment, ZhihuDailyNewsRepository.getInstance(
                ZhihuDailyNewsRemoteDataSource.getInstance(),
                ZhihuDailyNewsLocalDataSource.getInstance(getContext())
        ));

        new GuokrHandpickPresenter(guokrHandpickFragment, GuokrHandpickNewsRepository.getInstance(
                GuokrHandpickNewsRemoteDataSource.getInstance(),
                GuokrHandpickNewsLocalDataSource.getInstance(getContext())
        ));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstaceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, parent, false);
        initViews(v);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    mFab.hide();
                } else {
                    mFab.show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabLayout.getSelectedTabPosition() == 0) {
                    zhihuDailyFragment.showDatePickerDialog();
                } else {
                    doubanMomentFragment.showDatePickerDialog();
                }
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getChildFragmentManager();
        if (zhihuDailyFragment.isAdded()) {
            fm.putFragment(outState, zhihuDailyFragment.getClass().getSimpleName(), zhihuDailyFragment);
        }
        if (doubanMomentFragment.isAdded()) {
            fm.putFragment(outState, doubanMomentFragment.getClass().getSimpleName(), doubanMomentFragment);
        }
        if (guokrHandpickFragment.isAdded()) {
            fm.putFragment(outState, guokrHandpickFragment.getClass().getSimpleName(), guokrHandpickFragment);
        }
    }

    private void initViews(View v) {
        ViewPager mViewPager = v.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TimelineFragmentPagerAdapter(
                getChildFragmentManager(),
                getContext(),
                zhihuDailyFragment,
                doubanMomentFragment,
                guokrHandpickFragment));
        mViewPager.setOffscreenPageLimit(3);

        mTabLayout = v.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mFab = v.findViewById(R.id.fab);
    }


}
