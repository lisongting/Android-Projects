package com.lst.wanandroid.presenter.hierarchy;

import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;
import com.lst.wanandroid.core.event.CollectEvent;

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
                        mView.showCancelCollectArticleData();
                    }
                }));

    }
    @Override
    public void getKnowledgeHIerarchyDetailData(int page, int cid, boolean isShowError) {

    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {

    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {

    }
}
