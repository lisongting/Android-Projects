package com.lst.wanandroid.contract.main;


import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;

public interface CollectContract {

    interface View extends AbstractView {

        /**
         * Show collect list
         *
         * @param feedArticleListData FeedArticleListData
         */
        void showCollectList(FeedArticleListData feedArticleListData);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show Refresh event
         */
        void showRefreshEvent();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get collect list
         *
         * @param page page number
         * @param isShowError If show error
         */
        void getCollectList(int page, boolean isShowError);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectPageArticle(int position, FeedArticleData feedArticleData);


    }
}
