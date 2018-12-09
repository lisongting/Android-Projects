package com.lst.wanandroid.ui.mainpager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lst.wanandroid.R;
import com.lst.wanandroid.app.Constants;
import com.lst.wanandroid.base.fragment.BaseRootFragment;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.mainpager.MainPagerContract;
import com.lst.wanandroid.core.bean.main.banner.BannerData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.event.AutoLoginEvent;
import com.lst.wanandroid.core.event.LoginEvent;
import com.lst.wanandroid.core.event.SwitchNavigationEvent;
import com.lst.wanandroid.core.event.SwitchProjectEvent;
import com.lst.wanandroid.core.http.cookies.CookieManager;
import com.lst.wanandroid.presenter.mainpager.MainPagerPresenter;
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.GlideImageLoader;
import com.lst.wanandroid.utils.JudgeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainPagerFragment extends BaseRootFragment<MainPagerPresenter> implements MainPagerContract.View{

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.main_pager_recycler_view)
    RecyclerView mRecyclerView;

    private List<FeedArticleData> mFeedArticleDataList;
    private ArticleListAdapter mAdapter;
    private int articlePosition;
    private List<String> mBannerTitleList;
    private List<String> mBannerUrlList;
    private Banner mBanner;
    private boolean isRecreate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isRecreate = getArguments().getBoolean(Constants.ARG_PARAM1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    public static MainPagerFragment getInstance(boolean param1, String param2) {
        MainPagerFragment fragment = new MainPagerFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_pager;
    }

    @Override
    protected void initEventAndData(){
        super.initEventAndData();
        mFeedArticleDataList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mFeedArticleDataList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
                    return;
                }
                //记录点击的
                articlePosition = position;
                //todo:?
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity,
                        view, getString(R.string.share_view));
                FeedArticleData itemData = mAdapter.getData().get(position);
                //todo:isCollectPage参数
                JudgeUtils.startArticleDetailActivity(_mActivity, options,
                        itemData.getId(), itemData.getTitle(),
                        itemData.getLink(), itemData.isCollect(),
                        false, false);
            }
        });

        //每一个Item中的子控件被点击
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_search_pager_chapterName:
                        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                            return;
                        }
                        FeedArticleData feedArticleData = mAdapter.getData().get(position);
                        JudgeUtils.startKnowledgeHierarchyDetailActivity(_mActivity,true,
                                feedArticleData.getSuperChapterName(),
                                feedArticleData.getChapterName(),
                                feedArticleData.getChapterId());
                        break;
                    case R.id.item_search_pager_like_iv:
                        likeEvent(position);
                        break;
                    case R.id.item_search_pager_tag_red_tv:
                        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                            return;
                        }
                        FeedArticleData articleData = mAdapter.getData().get(position);
                        String superChapterName = articleData.getSuperChapterName();
                        if (superChapterName.contains(getString(R.string.open_project))) {
                            //todo:
                            RxBus.getDefault().post(new SwitchProjectEvent());
                        }else if(superChapterName.contains(getString(R.string.navigation))) {
                            RxBus.getDefault().post(new SwitchNavigationEvent());
                        }
                        break;
                    default:break;
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);

        LinearLayout mHeaderGroup = (LinearLayout) LayoutInflater.from(_mActivity)
                .inflate(R.layout.head_banner,null);
        mBanner = mHeaderGroup.findViewById(R.id.head_banner);
        //todo:？
        mHeaderGroup.removeView(mBanner);
        mAdapter.addHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);

        setRefresh();

        if (!TextUtils.isEmpty(mPresenter.getLoginAccount())
                && !TextUtils.isEmpty(mPresenter.getLoginPassword())
                && !isRecreate) {
            mPresenter.loadMainPagerData();
        } else {
            mPresenter.autoRefresh(true);
        }
        if (CommonUtil.isNetworkConnected()) {
            showLoginView();
        }
    }

    @Override
    public void showAutoLoginSuccess() {
        if (isAdded()) {
            CommonUtil.showSnackMessage(_mActivity, getString(R.string.auto_login_success));
            RxBus.getDefault().post(new AutoLoginEvent());
        }
    }

    @Override
    public void showAutoLoginFail() {
        mPresenter.setLoginState(false);
        CookieManager.clearAllCookies();
        RxBus.getDefault().post(new LoginEvent(false));
    }

    @Override
    public void showArticleList(FeedArticleListData feedArticleListData, boolean isRefresh) {
        if (mPresenter.getCurrentPage() == Constants.TYPE_MAIN_PAGER) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mFeedArticleDataList = feedArticleListData.getDatas();
            mAdapter.replaceData(feedArticleListData.getDatas());
        } else {
            mFeedArticleDataList.addAll(feedArticleListData.getDatas());
            mAdapter.addData(feedArticleListData.getDatas());
        }
        showNormal();
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtil.showSnackMessage(_mActivity, getString(R.string.collect_success));

    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtil.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showBannerDataList(List<BannerData> bannerDataList) {
        mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        mBannerUrlList = new ArrayList<>();
        for (BannerData b : bannerDataList) {
            mBannerTitleList.add(b.getTitle());
            bannerImageList.add(b.getImagePath());
            mBannerUrlList.add(b.getUrl());
        }

        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.setBannerTitles(mBannerTitleList);
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(bannerDataList.size() * 400);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                JudgeUtils.startArticleDetailActivity(_mActivity, null, 0, mBannerTitleList.get(position),
                        mBannerUrlList.get(position), false, false, true);
            }
        });
        mBanner.start();
    }

    @Override
    public void showLoginView() {
        mPresenter.getFeedArticleList(false);
    }

    @Override
    public void showLogoutView() {
        mPresenter.getFeedArticleList(false);
    }

    @Override
    public void showCollectSuccess() {
        if (mAdapter != null && mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(true);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showCancelCollectSuccess() {
        if (mAdapter != null&&mAdapter.getData().size()>articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(false);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showError(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        super.showError();
    }

    @Override
    public void useNightMode(boolean isNightMode) {

    }

    @Override
    public void reload() {
        if (mRefreshLayout != null && mPresenter != null
                && mRecyclerView.getVisibility() == View.INVISIBLE
                && CommonUtil.isNetworkConnected()) {
            mRefreshLayout.autoRefresh();
        }
    }

    //点击收藏按钮
    private void likeEvent(int articlePosition) {
        //如果没有登录，那么跳出登录界面让用户登录
        if (!mPresenter.getLoginStatus()) {
            startActivity(new Intent(_mActivity,LoginActivity.class));
            CommonUtil.showMessage(_mActivity, getString(R.string.login_tint));
            return;
        }
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= articlePosition) {
            return;
        }
        if (mAdapter.getData().get(articlePosition).isCollect()) {
            mPresenter.cancelCollectArticle(articlePosition, mAdapter.getData().get(articlePosition));
        } else {
            mPresenter.addCollectArticle(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    public void jumpToTheTop(){
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void setRefresh(){
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.autoRefresh(false);
                refreshLayout.finishRefresh(1000);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.loadMore();
                refreshLayout.finishLoadMore(1000);
            }
        });
    }




}
