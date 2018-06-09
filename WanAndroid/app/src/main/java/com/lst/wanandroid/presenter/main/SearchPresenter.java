package com.lst.wanandroid.presenter.main;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.contract.main.SearchContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.main.search.TopSearchData;
import com.lst.wanandroid.core.dao.HistoryData;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class SearchPresenter extends BasePresenter<SearchContract.View>
    implements SearchContract.Presenter{
    private DataManager dataManager;

    @Inject
    SearchPresenter(DataManager dataManager) {
        super(dataManager);
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(SearchContract.View view) {
        super.attachView(view);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return dataManager.loadAllHistoryData();
    }

    @Override
    public void addHistoryData(String data) {
        addSubscribe(Observable.create(new ObservableOnSubscribe<List<HistoryData>>(){
            @Override
            public void subscribe(ObservableEmitter<List<HistoryData>> e) throws Exception {
                List<HistoryData> historyDataList = dataManager.addHistoryData(data);
                e.onNext(historyDataList);
            }
        })
        .compose(RxUtils.rxSchedulerHelper())
        .subscribe(new Consumer<List<HistoryData>>() {
            @Override
            public void accept(List<HistoryData> historyData) throws Exception {
                mView.judgeToTheSearchListActivity();
            }
        }));
    }


    @Override
    public void getTopSearchData() {
        addSubscribe(dataManager.getTopSearchData()
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<List<TopSearchData>>(mView,
                    WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_top_data)) {
                @Override
                public void onNext(List<TopSearchData> topSearchData) {
                    mView.showTopSearchData(topSearchData);
                }
            }));
    }

    @Override
    public void clearHistoryData() {
        dataManager.clearHistoryData();
    }
}
