package com.paperfish.espresso.mvp.addpackage;

import com.paperfish.espresso.mvp.BasePresenter;
import com.paperfish.espresso.mvp.BaseView;

/**
 * Created by lisongting on 2017/7/27.
 */

public interface AddPackageContract {
    interface View extends BaseView<Presenter> {

        void showNumberExistError();

        void showNumberError();

        void setProgressIndicator(boolean loading);

        void showPackagesList();

        void showNetworkError();

    }

    interface Presenter extends BasePresenter {

        void savePackage(String number, String name, int color);

    }

}
