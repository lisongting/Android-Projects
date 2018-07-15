package com.lst.wanandroid.contract.hierarchy;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;

public interface KnowledgeHierarchyListContract {
    interface View extends AbstractView {
        void showKnowLedgeHierarchyDetailData(FeedArticleListData feedArticleListData);

        void showCollectArticleData(int positioin, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        void showJumpTheTop();

        void showReloadDetailEvent();
    }

    interface Presenter extends AbstractPresenter<View>{
        void getKnowledgeHierarchyDetailData(int page,int cid,boolean isShowError);

        void addCollectArticle(int position, FeedArticleData feedArticleData);

        void cancelCollectArticle(int position, FeedArticleData feedArticleData);
    }
}
