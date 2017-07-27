package com.paperfish.espresso.mvp.addpackage;

import com.paperfish.espresso.data.Company;
import com.paperfish.espresso.data.CompanyRecognition;
import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.data.PackageWithCompany;
import com.paperfish.espresso.data.source.CompaniesDataSource;
import com.paperfish.espresso.data.source.PackagesDataSource;
import com.paperfish.espresso.retrofit.RetrofitClient;
import com.paperfish.espresso.retrofit.RetrofitInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lisongting on 2017/7/27.
 */

public class AddPackagePresenter implements AddPackageContract.Presenter{

    private AddPackageContract.View view;

    private final PackagesDataSource packagesDataSource;

    private final CompaniesDataSource companiesDataSource;

    private CompositeDisposable compositeDisposable;

    public AddPackagePresenter(PackagesDataSource dataSource,
                               CompaniesDataSource companiesDataSource,
                               AddPackageContract.View view) {
        this.packagesDataSource = dataSource;
        this.companiesDataSource = companiesDataSource;
        this.view = view;
        //一般是在Presenter的构造器中，对传进来的View直接给它绑定当前的Presenter
        this.view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    //保存包裹
    @Override
    public void savePackage(String number, String name, int color) {
        compositeDisposable.clear();
        checkNumber(number, name, color);
    }

    private void checkNumber(final String number, final String name, final int color) {
        //如果包裹已经存在，则显示订单号已存在
        if (packagesDataSource.isPackageExist(number)) {
            view.showNumberExistError();
            return;
        }
        view.setProgressIndicator(true);

        Disposable disposable = RetrofitClient.getInstance()
                .create(RetrofitInterface.class)
                .query(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CompanyRecognition>(){
                    @Override
                    public void onNext(CompanyRecognition value) {
                        if (value.getAuto().size() > 0 && value.getAuto().get(0).getCompanyCode() != null) {
                            checkPackageLatestStatus(value.getAuto().get(0).getCompanyCode(), number, name, color);
                        }else{
                            view.showNumberExistError();
                            view.setProgressIndicator(false);
                        }
                    }
                    @Override
                    public void onComplete() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        view.showNumberError();
                        view.setProgressIndicator(false);
                        view.showNetworkError();
                    }
                });

        compositeDisposable.add(disposable);

    }

    //检查包裹最新的状态
    private void checkPackageLatestStatus(final String type, final String number, final String name, final int color) {
        Observable<Company> companyObservable = companiesDataSource
                .getCompany(type)
                .subscribeOn(Schedulers.io());

        Observable<Package> packageObservable = RetrofitClient
                .getInstance()
                .create(RetrofitInterface.class)
                .getPackageState(type,number)
                .subscribeOn(Schedulers.io());

        //zip操作符：将两个Observable对象压缩成一个Observable,具体压缩规则由BiFunction决定
        Disposable disposable = Observable
                .zip(packageObservable, companyObservable, new BiFunction<Package, Company, PackageWithCompany>(){
                    @Override
                    public PackageWithCompany apply(Package aPackage, Company company) throws Exception {
                        return new PackageWithCompany(aPackage, company);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PackageWithCompany>(){

                    @Override
                    public void onNext(PackageWithCompany value) {
                        if (value != null) {
                            Package p = value.getPkg();
                            if (p.getData() != null && p.getData().size() != 0) {
                                p.setReadable(true);
                                p.setPushable(true);
                            }
                            p.setCompany(type);
                            p.setCompanyChineseName(value.getCompany().getName());
                            p.setName(name);
                            p.setNumber(number);
                            p.setColorAvatar(color);
                            p.setTimestamp(System.currentTimeMillis());
                            packagesDataSource.savePackage(p);
                        }
                        view.showPackagesList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showNetworkError();
                        view.setProgressIndicator(false);
                    }

                    @Override
                    public void onComplete() {
                        view.setProgressIndicator(false);
                    }
                });
        compositeDisposable.add(disposable);
    }
}
