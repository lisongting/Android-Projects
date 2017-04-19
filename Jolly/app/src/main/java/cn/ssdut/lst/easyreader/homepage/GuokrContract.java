package cn.ssdut.lst.easyreader.homepage;

import java.util.ArrayList;

import cn.ssdut.lst.easyreader.BasePresenter;
import cn.ssdut.lst.easyreader.BaseView;
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface GuokrContract {
    interface View extends BaseView<ZhihuDailyContract.Presenter> {
        void showError();

        void showLoading();

        void stopLoading();

        void showResult(ArrayList<GuokrHandpickNews.result> list);

    }
    interface Presenter extends BasePresenter {

        void loadPosts(long date,boolean clearing);

        void refresh();

        void loadMore(long date);

        void startReading(int position);

        void feelLucky();
    }
}