package com.lst.wanandroid.ui.project;

import com.lst.wanandroid.base.fragment.BaseRootFragment;
import com.lst.wanandroid.contract.project.ProjectContract;
import com.lst.wanandroid.core.bean.project.ProjectClassifyData;

import java.util.List;

public class ProjectFragment extends BaseRootFragment<ProjectPresenter>
        implements ProjectContract.View{
    @Override
    public void showProjectClassifyData(List<ProjectClassifyData> projectClassifyDataList) {

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
