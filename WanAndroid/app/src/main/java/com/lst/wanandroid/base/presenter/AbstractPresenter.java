package com.lst.wanandroid.base.presenter;

import com.lst.wanandroid.base.AbstractView;

import io.reactivex.disposables.Disposable;

public interface AbstractPresenter<T extends AbstractView>{
    void attachView(T view);

    void detachView();

    void addRxBindingSubscribe(Disposable disposable);

    boolean getNightModeState();

    void setLoginState(boolean loginState);

    boolean getLoginStatus();

    String getLoginAccount();

    int getCurrentPage();

}
