package cn.ssdut.lst.easyreader.homepage;

import java.util.List;

import cn.ssdut.lst.easyreader.BasePresenter;
import cn.ssdut.lst.easyreader.BaseView;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter> {
        void showError();

        void showLoading();

        void stopLoading();

        void showResult(List<ZhihuDailyNews.Question> list);
    }
    interface Presenter extends BasePresenter {
        void loadPosts(long date,boolean clearing);

        void refresh();

        void loadMore(long date);

        void startReading(int position);

        void feelLucky();
    }
}
