package com.lst.wanandroid.presenter.hierarchy;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lst.wanandroid.core.event.CollectEvent;
import com.lst.wanandroid.core.event.KnowledgeJumpTopEvent;
import com.lst.wanandroid.core.event.ReloadDetailEvent;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class KnowledgeHierarchyListPresenter extends BasePresenter<KnowledgeHierarchyListContract.View>
    implements KnowledgeHierarchyListContract.Presenter{
    private DataManager dataManager;
    @Inject
    KnowledgeHierarchyListPresenter(DataManager dataManager){
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(KnowledgeHierarchyListContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent(){
        RxBus bus = RxBus.getDefault();
        //收藏
        addSubscribe(bus.toFlowable(CollectEvent.class)
            .filter(new Predicate<CollectEvent>() {
                @Override
                public boolean test(CollectEvent collectEvent) throws Exception {
                    return !collectEvent.isCancelCollectSuccess();
                }
            }).subscribe(new Consumer<CollectEvent>() {
                    @Override
                    public void accept(CollectEvent collectEvent) throws Exception {
                        mView.showCollectSuccess();
                    }
                }));

        //取消收藏
        addSubscribe(bus.toFlowable(CollectEvent.class)
            .filter(new Predicate<CollectEvent>() {
                @Override
                public boolean test(CollectEvent collectEvent) throws Exception {
                    return collectEvent.isCancelCollectSuccess();
                }
            }).subscribe(new Consumer<CollectEvent>() {
                    @Override
                    public void accept(CollectEvent collectEvent) throws Exception {
                        mView.showCancelCollectSuccess();
                    }
                }));

        addSubscribe(bus.toFlowable(KnowledgeJumpTopEvent.class)
            .subscribe(new Consumer<KnowledgeJumpTopEvent>() {
                @Override
                public void accept(KnowledgeJumpTopEvent knowledgeJumpTopEvent) throws Exception {
                    mView.showJumpTheTop();
                }
            }));

        addSubscribe(bus.toFlowable(ReloadDetailEvent.class)
            .subscribe(new Consumer<ReloadDetailEvent>() {
                @Override
                public void accept(ReloadDetailEvent reloadDetailEvent) throws Exception {
                    mView.showReloadDetailEvent();
                }
            }));

    }

    @Override
    public void getKnowledgeHierarchyDetailData(int page, int cid, boolean isShowError) {
        addSubscribe(dataManager.getKnowledgeHierarchyDetailData(page,cid)
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_knowledge_data)
                    ,isShowError) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    mView.showKnowLedgeHierarchyDetailData(feedArticleListData);
                }
            }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.addCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleCollectResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView,WanAndroidApp.getInstance().getString(R.string.collect_fail)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    feedArticleData.setCollect(true);
                    mView.showCollectArticleData(position,feedArticleData,feedArticleListData);
                }
            }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(dataManager.cancelCollectArticle(feedArticleData.getId())
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleCollectResult())
            .subscribeWith(new BaseObserver<FeedArticleListData>(mView, WanAndroidApp.getInstance().getString(R.string.cancel_collect_fail)) {
                @Override
                public void onNext(FeedArticleListData feedArticleListData) {
                    feedArticleData.setCollect(false);
                    mView.showCancelCollectArticleData(position,feedArticleData,feedArticleListData);
                }
            }));
    }
}
