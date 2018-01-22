package cn.lst.jolly.favorites;

import android.support.annotation.NonNull;

import java.util.List;

import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.ZhihuDailyNewsQuestion;
import cn.lst.jolly.data.source.datasource.DoubanMomentNewsDataSource;
import cn.lst.jolly.data.source.datasource.GuokrHandpickDataSource;
import cn.lst.jolly.data.source.datasource.ZhihuDailyNewsDataSource;
import cn.lst.jolly.data.source.repository.DoubanMomentNewsRepository;
import cn.lst.jolly.data.source.repository.GuokrHandpickNewsRepository;
import cn.lst.jolly.data.source.repository.ZhihuDailyNewsRepository;

/**
 * Created by lisongting on 2018/1/22.
 */

public class FavoritesPresenter implements FavoritesContract.Presenter {
    private final FavoritesContract.View mView;
    private final ZhihuDailyNewsRepository mZhihuRepository;
    private final DoubanMomentNewsRepository mDoubanRepository;
    private final GuokrHandpickNewsRepository mGuokrRepository;

    public FavoritesPresenter(FavoritesContract.View view,
                              ZhihuDailyNewsRepository zhihuRepository,
                              DoubanMomentNewsRepository doubanRepository,
                              GuokrHandpickNewsRepository guokrRepository) {
        mView = view;
        mView.setPresenter(this);
        this.mZhihuRepository = zhihuRepository;
        this.mDoubanRepository = doubanRepository;
        this.mGuokrRepository = guokrRepository;

    }

    @Override
    public void start() {

    }

    @Override
    public void loadFavorites() {
        mZhihuRepository.getFavorites(new ZhihuDailyNewsDataSource.LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull final List<ZhihuDailyNewsQuestion> zhihuList) {
                mDoubanRepository.getFavorites(new DoubanMomentNewsDataSource.LoadDoubanMomentDailyCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull final List<DoubanMomentNewsPosts> doubanList) {
                        mGuokrRepository.getFavorites(new GuokrHandpickDataSource.LoadGuokrHandpickNewsCallback() {
                            @Override
                            public void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> guokrList) {
                                if (mView.isActive()) {
                                    mView.showFavorites(zhihuList, doubanList, guokrList);
                                }
                                mView.setLoadingIndicator(false);
                            }

                            @Override
                            public void onDataNotAvailable() {
                                mView.setLoadingIndicator(false);
                            }
                        });
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
