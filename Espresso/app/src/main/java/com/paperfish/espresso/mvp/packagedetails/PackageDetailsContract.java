package com.paperfish.espresso.mvp.packagedetails;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.mvp.BasePresenter;
import com.paperfish.espresso.mvp.BaseView;

/**
 * Created by lisongting on 2017/8/5.
 */

public interface PackageDetailsContract {



    interface Presenter extends BasePresenter {
        void setPackageUnread();

        void refreshPackage();

        void deletePackage();

        void copyPackageNumber();

        void shareTo();

        String getPackageName();

        void updatePackageName(String name);

    }

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean loading);

        void showNetworkError();

        void showPackageDetails(@NonNull Package p);

        void setToolbarBackground(@DrawableRes int resId);

        void shareTo(@NonNull Package pack);

        void copyPackageNumber(@NonNull String packageId);

        void exit();
    }


}
