package com.lst.wanandroid.presenter.main;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.main.SearchListContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.event.CollectEvent;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class SearchListPresenter extends BasePresenter<SearchListContract.View>
    implements SearchListContract.Presenter{
    private DataManager dataManager;

    @Inject
    SearchListPresenter(DataManager dataManager) {
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(SearchListContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent(){
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .filter(new Predicate<CollectEvent>() {
                @Override
                public boolean test(CollectEvent collectEvent) throws Exception {
                    return !collectEvent.isCancelCollectSuccess();
                }
            })
            .subscribe(new Consumer<CollectEvent>() {
                @Override
                public void accept(CollectEvent collectEvent) throws Exception {
                    mView.showCollectSuccess();
                }
            }));

        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
            .filter(new Predicate<CollectEvent>() {
                @Override
                public boolean test(CollectEvent collectEvent) throws Exception {
                    return collectEvent.isCancelCollectSuccess();
                }
            })
            .subscribe(new Consumer<CollectEvent>() {
                @Override
                public void accept(CollectEvent collectEvent) throws Exception {
                    mView.showCancelCollectSuccess();
                }
            }));
    }

    public boolean getNightModeState(){
        return dataManager.getNightModeState();
    }

    @Override
    public void getSearchList(int page, String k, boolean isShowError) {
        addSubscribe(dataManager.getSearchList(page,k)
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_search_data_list)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    mView.showSearchList(feedArticleListData);
                }
            }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.addCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.collect_fail)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    mView.showCollectArticleData(position,feedArticleData,feedArticleListData);
                }
            }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.cancelCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.cancel_collect_fail)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    feedArticleData.setCollect(false);
                    mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListData);
                }
            }));
    }
}
