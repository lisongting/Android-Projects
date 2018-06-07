package com.lst.wanandroid.ui.main.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lst.wanandroid.R;
import com.lst.wanandroid.base.activity.BaseActivity;
import com.lst.wanandroid.base.fragment.BaseFragment;
import com.lst.wanandroid.contract.main.MainContract;
import com.lst.wanandroid.presenter.main.MainPresenter;
import com.lst.wanandroid.ui.hierarchy.KnowledgeHierarchyFragment;
import com.lst.wanandroid.ui.main.fragment.SearchDialogFragment;
import com.lst.wanandroid.ui.main.fragment.UsageDialogFragment;
import com.lst.wanandroid.ui.mainpager.MainPagerFragment;
import com.lst.wanandroid.ui.navigation.fragment.NavigationFragment;
import com.lst.wanandroid.ui.project.ProjectFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View{
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView titleTv;
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton fab;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
//    @BindView(R.id.nav_view)
//    NavigationView navgationView;
    @BindView(R.id.fragment_group)
    FrameLayout frameLayout;

    private ArrayList<BaseFragment> mFragments;
    private TextView mUsTv;
    private MainPagerFragment mMainPagerFragment;
    private KnowledgeHierarchyFragment mKnowledgeHierarchyFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment projectFramgment;
    private int mLastFgIndex;
    private UsageDialogFragment usageDialogFragment;
    private SearchDialogFragment searchDialogFragment;

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showSwitchProject() {

    }

    @Override
    public void showSwitchNavigation() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }
}
