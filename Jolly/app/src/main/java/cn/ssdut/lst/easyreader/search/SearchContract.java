package cn.ssdut.lst.easyreader.search;

import java.util.ArrayList;

import cn.ssdut.lst.easyreader.BasePresenter;
import cn.ssdut.lst.easyreader.BaseView;
import cn.ssdut.lst.easyreader.bean.BeanType;
import cn.ssdut.lst.easyreader.bean.DoubanMomentNews;
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;

/**
 * Created by lisongting on 2017/4/30.
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void showResult(ArrayList<ZhihuDailyNews.Question> zhihuList,
                        ArrayList<GuokrHandpickNews.result> guokrList,
                        ArrayList<DoubanMomentNews.posts> doubanList,
                        ArrayList<Integer> types);

        void showLoading();

        void stopLoading();
    }

    interface Presenter extends BasePresenter{
        void loadResult(String queryWords);

        void startReading(BeanType type, int pos);

    }
}
