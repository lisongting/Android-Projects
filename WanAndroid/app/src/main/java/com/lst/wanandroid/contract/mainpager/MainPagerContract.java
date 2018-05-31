package com.lst.wanandroid.contract.mainpager;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.banner.BannerData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;

import java.util.List;

public interface MainPagerContract {

    interface View extends AbstractView {
        void showAutoLoginSuccess();

        void showAutoLoginFail();

        void showArticleList(FeedArticleListData feedArticleListData, boolean isRefresh);

        void showCollectArticleData(int position, FeedArticleData feedArticleData,
                                    FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData,
                                          FeedArticleListData feedArticleListData);

        void showBannerDataList(List<BannerData> bannerDataList);
    }

    interface Presenter extends AbstractPresenter<View>{
        String getLoginPassword();

        void loadMainPagerData();

        void getFeedArticleList(boolean isShowError);

        void loadMoreData();

        void addCollectArticle(int position, FeedArticleData feedArticleData);

        void cancelCollectArticle(int position, FeedArticleData feedArticleData);

        void getBannerData(boolean isShowError);

        void autoRefresh(boolean isShowError);

        void loadMore();
    }
}
