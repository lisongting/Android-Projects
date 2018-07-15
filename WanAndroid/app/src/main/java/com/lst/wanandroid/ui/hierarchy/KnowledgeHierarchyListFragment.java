package com.lst.wanandroid.ui.hierarchy;

import com.lst.wanandroid.base.fragment.BaseRootFragment;
import com.lst.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.presenter.hierarchy.KnowledgeHierarchyListPresenter;

public class KnowledgeHierarchyListFragment extends BaseRootFragment<KnowledgeHierarchyListPresenter>
    implements KnowledgeHierarchyListContract.View{
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void showKnowLedgeHierarchyDetailData(FeedArticleListData feedArticleListData) {

    }

    @Override
    public void showCollectArticleData(int positioin, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {

    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {

    }

    @Override
    public void showJumpTheTop() {

    }

    @Override
    public void showReloadDetailEvent() {

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
}
