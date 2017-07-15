package com.paperfish.espresso.mvp.packages;

import android.support.annotation.NonNull;

import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.mvp.BasePresenter;
import com.paperfish.espresso.mvp.BaseView;

import java.util.List;

/**
 * Created by lisongting on 2017/7/15.
 */

public class PackagesContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showEmptyView(boolean toShow);

        void showPackages(@NonNull List<Package> list);

        void shareTo(@NonNull Package pack);

        void showPackageRemovedMsg(String packageName);

        void copyPackageNumber();

        void showNetworkError();

    }

    interface Presenter extends BasePresenter{
        void loadPackages();

        void refreshPackages();

        void markAllPacksRead();

        void setFiltering(@NonNull PackageFilterType requestType);

        PackageFilterType getFiltering();

        void setPackageReadable(@NonNull String packageId, boolean readable);

        void deletePackage(int position);

        void setShareData(@NonNull String packageId);

        void recoverPackage();
    }
}
