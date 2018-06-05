package com.lst.wanandroid.presenter.main;

import android.text.TextUtils;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.presenter.BasePresenter;
import com.lst.wanandroid.contract.main.LoginContract;
import com.lst.wanandroid.core.DataManager;
import com.lst.wanandroid.core.bean.main.login.LoginData;
import com.lst.wanandroid.utils.RxUtils;
import com.lst.wanandroid.widget.BaseObserver;

import javax.inject.Inject;

import io.reactivex.functions.Predicate;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter{

    private DataManager mDataManager;

    @Inject
    LoginPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void setLoginAccount(String account) {
        mDataManager.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        mDataManager.setLoginPassword(password);
    }

    @Override
    public void getLoginData(String username, String password) {
        addSubscribe(mDataManager.getLoginData(username,password)
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .subscribeWith(new BaseObserver<LoginData>(mView, WanAndroidApp.getInstance().getString(R.string.login_fail)) {
                @Override
                public void onNext(LoginData loginData) {
                    mView.showLoginData(loginData);
                }
            }));
    }

    @Override
    public void getRegisterData(String username, String password, String rePassword) {
        addSubscribe(mDataManager.getRegisterData(username,password,rePassword)
            .compose(RxUtils.rxSchedulerHelper())
            .compose(RxUtils.handleResult())
            .filter(new Predicate<LoginData>() {
                @Override
                public boolean test(LoginData loginData) throws Exception {
                    return !TextUtils.isEmpty(username)
                            &&!TextUtils.isEmpty(password)
                            &&!TextUtils.isEmpty(rePassword);
                }
            }).subscribeWith(new BaseObserver<LoginData>(mView, WanAndroidApp.getInstance().getString(R.string.register_fail)) {
                    @Override
                    public void onNext(LoginData loginData) {
                        mView.showRegisterData(loginData);
                    }
                })
        );
    }
}
