package com.paperfish.espresso.mvp.packagedetails;

import com.paperfish.espresso.R;
import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.data.source.PackagesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by lisongting on 2017/8/6.
 */

public class PackageDetailsPresenter implements PackageDetailsContract.Presenter {
    private PackageDetailsContract.View view;
    private PackagesRepository packagesRepository;
    private CompositeDisposable compositeDisposable;
    private String packageName;
    private String packageId;

    public PackageDetailsPresenter(String packageId,
                                   PackagesRepository packagesRepository,
                                   PackageDetailsContract.View view) {
        this.packageId = packageId;
        this.view = view;
        this.packagesRepository = packagesRepository;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }
    @Override
    public void subscribe() {
        openDetail();
    }

    private void openDetail() {
        Disposable disposable = packagesRepository
                .getPackage(packageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Package>(){
                    @Override
                    public void onNext(Package value) {
                        packageName = value.getName();
                        view.showPackageDetails(value);

                        int state = Integer.parseInt(value.getState());
                        if (state == Package.STATUS_FAILED) {
                            view.setToolbarBackground(R.drawable.banner_background_error);
                        } else if (state == Package.STATUS_DELIVERED) {
                            view.setToolbarBackground(R.drawable.banner_background_delivered);
                        }else{
                            view.setToolbarBackground(R.drawable.banner_background_on_the_way);
                        }
                    }

                    public void onError(Throwable throwable) {

                    }

                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);

    }
    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }

    @Override
    public void setPackageUnread() {
        packagesRepository.setPackageReadable(packageId, true);
        view.exit();
    }

    @Override
    public void refreshPackage() {
        Disposable disposable = packagesRepository.refreshPackage(packageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Package>(){
                    @Override
                    public void onNext(Package p) {
                        view.showPackageDetails(p);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        view.setLoadingIndicator(false);
                        view.showNetworkError();
                    }

                    @Override
                    public void onComplete() {
                        view.setLoadingIndicator(false);
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void deletePackage() {
        packagesRepository.deletePackage(packageId);
        view.exit();
    }

    @Override
    public void copyPackageNumber() {
        view.copyPackageNumber(packageId);
    }

    @Override
    public void shareTo() {
        Disposable disposable = packagesRepository
                .getPackage(packageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Package>(){
                    @Override
                    public void onNext(Package p) {
                        view.shareTo(p);
                    }

                    @Override
                    public void onError(Throwable throwable) {}

                    @Override
                    public void onComplete(){}
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public void updatePackageName(String name) {
        packagesRepository.updatePackageName(packageId, name);
        openDetail();
    }
}
