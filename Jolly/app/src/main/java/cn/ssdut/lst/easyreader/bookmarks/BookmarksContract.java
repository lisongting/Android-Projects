package cn.ssdut.lst.easyreader.bookmarks;

import java.util.ArrayList;

import cn.ssdut.lst.easyreader.BasePresenter;
import cn.ssdut.lst.easyreader.BaseView;
import cn.ssdut.lst.easyreader.bean.BeanType;
import cn.ssdut.lst.easyreader.bean.DoubanMomentNews;
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;

/**
 * Created by lisongting on 2017/5/12.
 */

public class BookmarksContract {

    interface View extends BaseView<Presenter> {
        void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList,
                         ArrayList<GuokrHandpickNews.result> guokrList,
                         ArrayList<DoubanMomentNews.posts> doubanList,
                         ArrayList<Integer> types);

        void notifyDataChanged();

        void stopLoading();

        void showLoading();
    }

    interface Presenter extends BasePresenter {
        void loadResults(boolean refresh);

        void startReading(BeanType type, int position);

        void checkForFreshData();

        void feelLucky();
    }

}
