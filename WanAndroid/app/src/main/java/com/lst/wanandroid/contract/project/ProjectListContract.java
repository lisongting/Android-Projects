package com.lst.wanandroid.contract.project;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.bean.project.ProjectListData;

public interface ProjectListContract {

    interface View extends AbstractView {

        /**
         * Show project list data
         *
         * @param projectListData ProjectListData
         */
        void showProjectListData(ProjectListData projectListData);

        /**
         * Show article list
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show jump to the top
         */
        void showJumpToTheTop();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get project list data
         *
         * @param page page num
         * @param cid second page id
         * @param isShowError If show error
         */
        void getProjectListData(int page, int cid, boolean isShowError);

        /**
         * Add collect outside article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void addCollectOutsideArticle(int position, FeedArticleData feedArticleData);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectArticle(int position, FeedArticleData feedArticleData);
    }

}
