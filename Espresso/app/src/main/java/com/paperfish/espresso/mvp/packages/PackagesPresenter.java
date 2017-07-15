package com.paperfish.espresso.mvp.packages;

import android.support.annotation.NonNull;

import com.paperfish.espresso.data.source.PackagesRepository;

/**
 * Created by lisongting on 2017/7/15.
 */

public class PackagesPresenter implements PackagesContract.Presenter {
    private PackagesContract.View view;
    private PackagesRepository packagesRepository;

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadPackages() {

    }

    @Override
    public void refreshPackages() {

    }

    @Override
    public void markAllPacksRead() {

    }

    @Override
    public void setFiltering(@NonNull PackageFilterType requestType) {

    }

    @Override
    public PackageFilterType getFiltering() {
        return null;
    }

    @Override
    public void setPackageReadable(@NonNull String packageId, boolean readable) {

    }

    @Override
    public void deletePackage(int position) {

    }

    @Override
    public void setShareData(@NonNull String packageId) {

    }

    @Override
    public void recoverPackage() {

    }
}
