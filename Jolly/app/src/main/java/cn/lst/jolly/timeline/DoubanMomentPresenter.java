
package cn.lst.jolly.timeline;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.source.datasource.DoubanMomentNewsDataSource;
import cn.lst.jolly.data.source.repository.DoubanMomentNewsRepository;


public class DoubanMomentPresenter implements DoubanMomentContract.Presenter {

    @NonNull
    private final DoubanMomentContract.View mView;

    @NonNull
    private final DoubanMomentNewsRepository mRepository;

    public DoubanMomentPresenter(@NonNull DoubanMomentContract.View view,
                                 @NonNull DoubanMomentNewsRepository repository) {
        this.mView = view;
        this.mRepository = repository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(boolean forceUpdate, boolean clearCache, long date) {

        mRepository.getDoubanMomentNews(forceUpdate, clearCache, date, new DoubanMomentNewsDataSource.LoadDoubanMomentDailyCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<DoubanMomentNewsPosts> list) {
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
