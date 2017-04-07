package cn.ssdut.lst.easyreader.about;


import cn.ssdut.lst.easyreader.BasePresenter;
import cn.ssdut.lst.easyreader.BaseView;

/**
 * Created by Administrator on 2017/4/5.
 */

public interface AboutContract {
    interface View extends BaseView<Presenter> {
        void showRateError();

        void showFeedbackError();

        void showBrowserNotFoundError();

    }

    interface Presenter extends BasePresenter{
        void rate();

        void openLicense();

        void followOnGithub();

        void followOnZhihu();

        void feedback();

        void donate();

        void showEasterEgg();

    }

}
