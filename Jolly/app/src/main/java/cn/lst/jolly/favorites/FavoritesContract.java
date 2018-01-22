package cn.lst.jolly.favorites;

import java.util.List;

import cn.lst.jolly.BasePresenter;
import cn.lst.jolly.BaseView;
import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.ZhihuDailyNewsQuestion;

/**
 * Created by lisongting on 2018/1/22.
 */

public interface FavoritesContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void setLoadingIndicator(boolean active);

        void showFavorites(List<ZhihuDailyNewsQuestion> zhihuList,
                           List<DoubanMomentNewsPosts> doubanList,
                           List<GuokrHandpickNewsResult> guokrList);
    }

    interface Presenter extends BasePresenter{
        void loadFavorites ();
    }
}
