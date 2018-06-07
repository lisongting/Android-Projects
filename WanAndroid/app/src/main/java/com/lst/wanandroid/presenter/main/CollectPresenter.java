package com.lst.wanandroid.presenter.main;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.main.CollectContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.event.CollectEvent;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter{

    private DataManager dataManager;

    @Inject
    CollectPresenter(DataManager dataManager) {
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(CollectContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent(){
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .subscribe(new Consumer<CollectEvent>() {
                @Override
                public void accept(CollectEvent collectEvent) throws Exception {
                    mView.showRefreshEvent();
                }
            }));
    }

    @Override
    public void getCollectList(int page, boolean isShowError) {
        addSubscribe(dataManager.getCollectList(page)
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_collection_data)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    mView.showCollectList(feedArticleListData);
                }
            }));
    }

    @Override
    public void cancelCollectPageArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.cancelCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView, WanAndroidApp.getInstance().getString(R.string.cancel_collect_fail)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    feedArticleData.setCollect(false);
                    mView.showCancelCollectPageArticleData(position, feedArticleData, feedArticleListData);
                }
            }));
    }
}
