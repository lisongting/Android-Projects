package cn.lst.jolly.timeline;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.ZhihuDailyNewsQuestion;
import cn.lst.jolly.data.source.datasource.ZhihuDailyNewsDataSource;
import cn.lst.jolly.data.source.repository.ZhihuDailyNewsRepository;

/**
 * Created by lisongting on 2018/1/2.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private final ZhihuDailyContract.View mView;
    private final ZhihuDailyNewsRepository mRepository;

    public ZhihuDailyPresenter(ZhihuDailyContract.View view, ZhihuDailyNewsRepository repository) {
        this.mView = view;
        this.mRepository = repository;
        this.mView.setPresenter(this);
    }
    @Override
    public void start() {

    }

    @Override
    public void loadNews(boolean forceUpdate, boolean clearCache, long date) {
        mRepository.getZhihuDailyNews(forceUpdate, clearCache, date, new ZhihuDailyNewsDataSource.LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyNewsQuestion> list) {
                if (mView.isActive()) {
                    mView.showResult(list);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }
        });
    }
}
