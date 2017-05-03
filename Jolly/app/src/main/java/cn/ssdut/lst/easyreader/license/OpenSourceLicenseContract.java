package cn.ssdut.lst.easyreader.license;

import cn.ssdut.lst.easyreader.BasePresenter;
import cn.ssdut.lst.easyreader.BaseView;

/**
 * Created by lisongting on 2017/4/30.
 */

public interface OpenSourceLicenseContract {
    interface View extends BaseView<Presenter> {
        void loadLicense(String path);
    }

    interface Presenter extends BasePresenter{

    }
}
