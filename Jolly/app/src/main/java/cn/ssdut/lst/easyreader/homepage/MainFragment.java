package cn.ssdut.lst.easyreader.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.adapter.MainPagerAdapter;

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

    private ZhihuDailyPresenter zhihuDailyPresenter;
    private DoubanMomentPresenter doubanMomentPresenter;
    private GuokrPresenter guokrPresenter;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();

        if (savedInstanceState != null) {
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState, "zhihu");
            guokrFragment = (GuokrFragment) manager.getFragment(savedInstanceState, "guokr");
            doubanMomentFragment = (DoubanMomentFragment) manager.getFragment(savedInstanceState, "douban");
        } else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            guokrFragment = GuokrFragment.newInstance();
            doubanMomentFragment = DoubanMomentFragment.newInstance();
        }

        zhihuDailyPresenter = new ZhihuDailyPresenter(context, zhihuDailyFragment);
        guokrPresenter = new GuokrPresenter(context, guokrFragment);
        doubanMomentPresenter = new DoubanMomentPresenter(context, doubanMomentFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, viewGroup, false);

        initViews(view);

        setHasOptionsMenu(true);

        //当tab为果壳精选时，隐藏fab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                if (tab.getPosition() == 1) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void initViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        adapter = new MainPagerAdapter(
                getChildFragmentManager(),
                context,
                zhihuDailyFragment,
                guokrFragment,
                doubanMomentFragment
        );

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_feel_lucky) {
            feelLuckey();
        }
        return true;
    }

    private void feelLuckey() {
        Random random = new Random();
        int type = random.nextInt(3);
        switch (type) {
            case 0:
                zhihuDailyPresenter.feelLucky();
                break;
            case 1:
                guokrPresenter.feelLucky();
                break;
            default:
                doubanMomentPresenter.feelLucky();
                break;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "zhihu", zhihuDailyFragment);
        manager.putFragment(outState, "guokr", guokrFragment);
        manager.putFragment(outState, "douban", doubanMomentFragment);
    }

    public MainPagerAdapter getAdapter() {
        return adapter;
    }

}
