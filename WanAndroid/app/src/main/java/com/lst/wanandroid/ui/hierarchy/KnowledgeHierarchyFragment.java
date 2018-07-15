package com.lst.wanandroid.ui.hierarchy;

import com.lst.wanandroid.base.fragment.BaseRootFragment;
import com.lst.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.lst.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lst.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;

import java.util.List;

public class KnowledgeHierarchyFragment extends BaseRootFragment<KnowledgeHierarchyPresenter>
            implements KnowledgeHierarchyContract.View{
    @Override
    public void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList) {

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
