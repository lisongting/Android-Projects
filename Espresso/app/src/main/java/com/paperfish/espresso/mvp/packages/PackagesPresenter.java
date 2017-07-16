package com.paperfish.espresso.mvp.packages;

import android.support.annotation.NonNull;

import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.data.source.PackagesRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lisongting on 2017/7/15.
 */

public class PackagesPresenter implements PackagesContract.Presenter {
    private PackagesContract.View view;
    private PackagesRepository packagesRepository;
    private CompositeDisposable compositeDisposable;
    private PackageFilterType currentFiltering = PackageFilterType.ALL_PACKAGES;
    private Package mayRemovePackage;

    public PackagesPresenter(PackagesContract.View view,
                             PackagesRepository packagesRepository) {
        this.view = view;
        this.packagesRepository = packagesRepository;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadPackages();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }

    //获取所有包裹然后展示在view上
    @Override
    public void loadPackages() {
        compositeDisposable.clear();
        //RxJava的代码还看不太懂，先写下来吧

        //创建一个disposable对象
        Disposable disposable = packagesRepository
                .getPackages()
                .flatMap(new Function<List<Package>,ObservableSource<Package>>(){
                    @Override
                    public ObservableSource<Package> apply(List<Package> list)throws Exception{
                        return Observable.fromIterable(list);
                    }

                })
                .filter(new Predicate<Package>(){
                    //返回true的才会把observable发给Observer。返回false的就会被过滤
                    @Override
                    public boolean test(Package aPackage) throws Exception {
                        int state = Integer.parseInt(aPackage.getState());
                        switch (currentFiltering) {
                            case ON_THE_WAY_PACKAGES:
                                return state != Package.STATUS_DELIVERED;
                            case DELIVERED_PACKAGES:
                                return state != Package.STATUS_DELIVERED;
                            case ALL_PACKAGES:
                                return true;
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Package>>(){
                    @Override
                    public void onNext(List<Package> value) {
                        view.showPackages(value);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        view.showEmptyView(true);
                        view.setLoadingIndicator(false);
                    }

                    @Override
                    public void onComplete() {
                        view.setLoadingIndicator(false);
                    }
                });
        //把创建好的disposable放到compositeDisposable中
        compositeDisposable.add(disposable);

    }

    //刷新包裹数据
    @Override
    public void refreshPackages() {
        Disposable disposable = packagesRepository
                .refreshPackages()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Package>>(){
                    @Override
                    public void onNext(List<Package> list) {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        view.setLoadingIndicator(false);
                        view.showNetworkError();
                    }

                    @Override
                    public void onComplete() {
                        view.setLoadingIndicator(false);
                        loadPackages();
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void markAllPacksRead() {
        packagesRepository.setAllPackagesRead();
        loadPackages();
    }

    @Override
    public void setFiltering(@NonNull PackageFilterType requestType) {
        currentFiltering = requestType;
    }

    @Override
    public PackageFilterType getFiltering() {
        return currentFiltering;
    }

    @Override
    public void setPackageReadable(@NonNull String packageId, boolean readable) {
        packagesRepository.setPackageReadable(packageId,readable);
        loadPackages();
    }

    @Override
    public void deletePackage(final int position) {
        if (position < 0) {
            return;
        }

        Disposable disposable = packagesRepository
                .getPackages()
                .flatMap(new Function<List<Package>, ObservableSource<Package>>() {
                    @Override
                    public ObservableSource<Package> apply(List<Package> list) throws Exception {
                        return Observable.fromIterable(list);
                    }
                })
                .filter(new Predicate<Package>() {
                    @Override
                    public boolean test(Package aPackage) throws Exception {
                        int state = Integer.parseInt(aPackage.getState());
                        switch (currentFiltering) {
                            case ON_THE_WAY_PACKAGES:
                                return state != Package.STATUS_DELIVERED;
                            case DELIVERED_PACKAGES:
                                return state == Package.STATUS_DELIVERED;
                            case ALL_PACKAGES:
                                return true;
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Package>>() {
                    @Override
                    public void onNext(List<Package> value) {
                        mayRemovePackage = value.get(position);
                        packagesRepository.deletePackage(mayRemovePackage.getNumber());
                        value.remove(position);
                        view.showPackages(value);
                        view.showPackageRemovedMsg(mayRemovePackage.getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void setShareData(@NonNull final String packageId) {
        Disposable disposable = packagesRepository
                .getPackage(packageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Package>(){

                    @Override
                    public void onNext(Package value) {
                        view.shareTo(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void recoverPackage() {
        if (mayRemovePackage != null) {
            packagesRepository.savePackage(mayRemovePackage);
        }
        loadPackages();
    }
}
