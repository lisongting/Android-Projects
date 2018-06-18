package com.lst.wanandroid.presenter.hierarchy;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class KnowledgeHierarchyPresenter extends BasePresenter<KnowledgeHierarchyContract.View>
    implements KnowledgeHierarchyContract.Presenter{

    private DataManager dataManager;

    @Inject
    KnowledgeHierarchyPresenter(DataManager dataManager) {
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void getKnowledgeHierarchyData(boolean isShowError) {
        addSubscribe(dataManager.getKnowledgeHierarchyData()
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<List<KnowledgeHierarchyData>>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_knowledge_data), isShowError) {
                @Override
                public void onNext(List<KnowledgeHierarchyData> list) {
                    mView.showKnowledgeHierarchyData(list);
                }
            }));
    }
}
