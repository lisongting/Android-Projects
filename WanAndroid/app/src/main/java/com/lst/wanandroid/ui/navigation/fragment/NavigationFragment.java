package com.lst.wanandroid.ui.navigation.fragment;

import com.lst.wanandroid.base.fragment.BaseRootFragment;
import com.lst.wanandroid.contract.navigation.NavigationContract;
import com.lst.wanandroid.core.bean.navigation.NavigationListData;

import java.util.List;

public class NavigationFragment extends BaseRootFragment<NavigationPresenter>
        implements NavigationContract.View{
    @Override
    public void showNavigationListData(List<NavigationListData> navigationDataList) {

    }

    @Override
    public void useNightMode(boolean isNightMode) {

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
}
