package com.lst.wanandroid.ui.main.fragment;

import com.lst.wanandroid.base.fragment.BaseDialogFragment;
import com.lst.wanandroid.contract.main.UsageDialogContract;
import com.lst.wanandroid.core.bean.main.search.UsefulSiteData;
import com.lst.wanandroid.presenter.main.UsageDialogPresenter;

import java.util.List;

public class UsageDialogFragment extends BaseDialogFragment<UsageDialogPresenter>
    implements UsageDialogContract.View{

    @Override
    public void showUsefulSites(List<UsefulSiteData> usefulSiteDataList) {

    }

    @Override
    public void useNightMode(boolean isNightMode) {

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
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }
}
