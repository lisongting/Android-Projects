package com.lst.wanandroid.contract.main;


import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;

public interface SearchListContract {

    interface View extends AbstractView {

        /**
         * Show search list
         *
         * @param feedArticleListData FeedArticleListData
         */
        void showSearchList(FeedArticleListData feedArticleListData);

        /**
         * Show collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 搜索
         * @param page page
         * @param k POST search key
         * @param isShowError If show error
         */
        void getSearchList(int page, String k, boolean isShowError);

        /**
         * Add collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void addCollectArticle(int position, FeedArticleData feedArticleData);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectArticle(int position, FeedArticleData feedArticleData);

    }

}
