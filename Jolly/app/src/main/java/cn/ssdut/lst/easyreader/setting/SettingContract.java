package cn.ssdut.lst.easyreader.setting;

import android.support.v7.preference.Preference;

import cn.ssdut.lst.easyreader.BasePresenter;
import cn.ssdut.lst.easyreader.BaseView;

/**
 * Created by lisongting on 2017/5/3.
 */

public class SettingContract {
    interface View extends BaseView<Presenter> {
        void showCleanGlideCacheDone();
    }

    interface Presenter extends BasePresenter{
        void setNoPictureMode(Preference preference);

        void setInAppBrowser(Preference preference);

        void cleanGlideCache();

        void setTimeOfSavingArticles(Preference preference, Object newValue);

        String getTimeSummary();
    }
}
