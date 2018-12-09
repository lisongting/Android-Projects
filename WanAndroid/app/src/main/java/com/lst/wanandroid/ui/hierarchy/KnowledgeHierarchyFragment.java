package com.lst.wanandroid.ui.hierarchy;

import android.support.v7.widget.RecyclerView;

import com.lst.wanandroid.R;
import com.lst.wanandroid.base.fragment.BaseRootFragment;
import com.lst.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.lst.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lst.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class KnowledgeHierarchyFragment extends BaseRootFragment<KnowledgeHierarchyPresenter>
            implements KnowledgeHierarchyContract.View{

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_recycler_view)
    RecyclerView mRecyclerView;

    private List<KnowledgeHierarchyData>

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
