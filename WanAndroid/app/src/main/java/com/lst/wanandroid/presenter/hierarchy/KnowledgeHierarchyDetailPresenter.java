package com.lst.wanandroid.presenter.hierarchy;

import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.component.RxBus;
import com.lst.wanandroid.contract.hierarchy.KnowledgeHierarchyDetailContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.event.SwitchNavigationEvent;
import com.lst.wanandroid.core.event.SwitchProjectEvent;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class KnowledgeHierarchyDetailPresenter extends BasePresenter<KnowledgeHierarchyDetailContract.View>
    implements KnowledgeHierarchyDetailContract.Presenter{

    private DataManager dataManager;

    @Inject
    KnowledgeHierarchyDetailPresenter(DataManager dataManager) {
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(KnowledgeHierarchyDetailContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(SwitchProjectEvent.class)
                .subscribe(new Consumer<SwitchProjectEvent>() {
                    @Override
                    public void accept(SwitchProjectEvent switchProjectEvent) throws Exception {
                        mView.showSwitchProject();
                    }
                }));
        addSubscribe(RxBus.getDefault().toFlowable(SwitchNavigationEvent.class)
            .subscribe(new Consumer<SwitchNavigationEvent>() {
                @Override
                public void accept(SwitchNavigationEvent switchNavigationEvent) throws Exception {
                    mView.showSwitchNavigation();
                }
            }));
    }

}
