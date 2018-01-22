package cn.lst.jolly.timeline;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.source.datasource.GuokrHandpickDataSource;
import cn.lst.jolly.data.source.repository.GuokrHandpickNewsRepository;

/**
 * Created by lisongting on 2018/1/16.
 */

public class GuokrHandpickPresenter implements GuokrHandpickContract.Presenter{
    private final GuokrHandpickContract.View mView;
    private final GuokrHandpickNewsRepository mRepository;

    public GuokrHandpickPresenter(GuokrHandpickContract.View view, GuokrHandpickNewsRepository repository) {
        this.mView = view;
        this.mRepository = repository;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(boolean forceUpdate, boolean clearCache, int offset, int limit) {
        mRepository.getGuokrHandpickNews(forceUpdate, clearCache, offset, limit,
                new GuokrHandpickDataSource.LoadGuokrHandpickNewsCallback() {
            @Override
            public void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> list) {
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
