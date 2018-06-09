package com.lst.wanandroid.presenter.main;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.contract.main.UsageDialogContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.main.search.UsefulSiteData;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

public class UsageDialogPresenter extends BasePresenter<UsageDialogContract.View>
    implements UsageDialogContract.Presenter{

    private DataManager dataManager;

    @Inject
    UsageDialogPresenter(DataManager dataManager) {
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void getUsefulSites() {
        addSubscribe(dataManager.getUsefulSites()
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<List<UsefulSiteData>>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_useful_sites_data)) {
                @Override
                public void onNext(List<UsefulSiteData> usefulSiteData) {
                    mView.showUsefulSites(usefulSiteData);
                }
            }));
    }
}
