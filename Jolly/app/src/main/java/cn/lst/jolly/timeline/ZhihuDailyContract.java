package cn.lst.jolly.timeline;

import java.util.List;

import cn.lst.jolly.BasePresenter;
import cn.lst.jolly.BaseView;
import cn.lst.jolly.data.ZhihuDailyNewsQuestion;

/**
 * Created by lisongting on 2018/1/2.
 */

public interface ZhihuDailyContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();

        void setLoadingIndicator(boolean active);

        void showResult(List<ZhihuDailyNewsQuestion> list);
    }

    interface Presenter extends BasePresenter{
        void loadNews(boolean forceUpdate, boolean clearCache, long date);
    }
}
