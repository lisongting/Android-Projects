package com.lst.wanandroid.contract.main;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.tbruyelle.rxpermissions2.RxPermissions;

public interface ArticleDetailContract {

    interface View extends AbstractView {
        void showCollectArticleData(FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(FeedArticleListData feedArticleListData);

        void shareEvent();

        void showError();
    }

    interface Presenter extends AbstractPresenter<View>{
        boolean getAutoCacheState();

        boolean getNoImageState();

        void addCollectArticle(int id);

        void cancelCollectPageArticle(int id);

        void shareEventPermissionVerify(RxPermissions rxPermissions);
    }
}
