package com.lst.wanandroid.ui.main.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lst.wanandroid.R;
import com.lst.wanandroid.app.Constants;
import com.lst.wanandroid.base.fragment.BaseRootFragment;
import com.lst.wanandroid.contract.main.CollectContract;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.presenter.main.CollectPresenter;
import com.lst.wanandroid.ui.mainpager.ArticleListAdapter;
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.JudgeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CollectFragment extends BaseRootFragment<CollectPresenter> implements CollectContract.View {
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.collect_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.collect_floating_action_btn)
    FloatingActionButton floatingActionButton;

    private boolean isRefresh = true;
    private int mCurrentPage;
    private List<FeedArticleData> mArticles;
    private ArticleListAdapter mAdapter;
    private ActivityOptions activityOptions;

    @Override
    public void initEventAndData(){
        super.initEventAndData();
        initView();
        setRefresh();
        if (CommonUtil.isNetworkConnected()) {
            showLoading();
        }
    }

    private void initView(){
        mArticles = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_search_page, mArticles);
        mAdapter.isCollectPage();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                    return;
                }
                //当Item被点击的时候，以Item作为视图切换的中间动画组件,启动新的Activity
                activityOptions = ActivityOptions.makeSceneTransitionAnimation(_mActivity,
                        view, getString(R.string.share_view));
                //启动文章详情页面
                FeedArticleData data = mAdapter.getData().get(position);
                JudgeUtils.startArticleDetailActivity(_mActivity,activityOptions,
                        data.getId(),data.getTitle(),data.getLink(),true,true,false);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_search_pager_chapterName:
                        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                            return;
                        }
                        JudgeUtils.startKnowledgeHierarchyDetailActivity(_mActivity,
                                true,mAdapter.getData().get(position).getSuperChapterName(),
                                mAdapter.getData().get(position).getChapterName(),
                                mAdapter.getData().get(position).getChapterId());
                        break;
                    case R.id.item_search_pager_like_iv:
                        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                            return;
                        }
                        mPresenter.cancelCollectPageArticle(position,mAdapter.getData().get(position));
                        break;
                    default:break;
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mPresenter.getCollectList(mCurrentPage, true);
    }

    @Override
    public void showCollectList(FeedArticleListData feedArticleListData) {
        if (mAdapter != null) {
            return;
        }
        mArticles = feedArticleListData.getDatas();
        if (isRefresh) {
            mAdapter.replaceData(mArticles);
        } else {
            if (mArticles.size() > 0) {
                mArticles.addAll(feedArticleListData.getDatas());
                mAdapter.addData(feedArticleListData.getDatas());
            } else {
                if (mAdapter.getData().size() != 0) {
                    CommonUtil.showMessage(_mActivity, getString(R.string.load_more_no_data));
                }
            }
        }
        if (mAdapter.getData().size() == 0) {
            CommonUtil.showSnackMessage(_mActivity, getString(R.string.no_collect));
        }
        showNormal();
    }

    @Override
    public void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData,
                                                 FeedArticleListData feedArticleListData) {
        mAdapter.remove(position);
        CommonUtil.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showRefreshEvent() {
        if (isVisible()) {
            mCurrentPage = 0;
            isRefresh = true;
            mPresenter.getCollectList(mCurrentPage, false);
        }
    }

    @OnClick({R.id.collect_floating_action_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_floating_action_btn:
                //当点击到悬浮按钮时，跳转到列表的起始位置
                mRecyclerView.smoothScrollToPosition(0);
                break;
            default:break;
        }
    }

    @Override
    public void useNightMode(boolean isNightMode) {

    }

    @Override
    public void reload() {
        mRefreshLayout.autoRefresh();
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

    public static CollectFragment getInstance(String param1, String param2) {
        CollectFragment fragment = new CollectFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARG_PARAM1, param1);
        bundle.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    private void setRefresh(){
        mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                showRefreshEvent();
                refreshLayout.finishRefresh(1000);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mCurrentPage ++;
                isRefresh = false;
                mPresenter.getCollectList(mCurrentPage, false);
                refreshLayout.finishLoadMore(1000);
            }
        });
    }
}
