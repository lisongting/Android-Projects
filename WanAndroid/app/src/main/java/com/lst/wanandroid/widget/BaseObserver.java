package com.lst.wanandroid.widget;

import android.text.TextUtils;

import com.lst.wanandroid.R;
import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.core.http.exception.ServerException;
import com.lst.wanandroid.utils.LogHelper;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T> extends ResourceObserver<T> {
    private AbstractView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseObserver(AbstractView abstractView) {
        this.mView = abstractView;
    }

    protected BaseObserver(AbstractView view, String mErrorMsg) {
        this.mView = view;
        this.mErrorMsg = mErrorMsg;
    }

    protected BaseObserver(AbstractView view, boolean isShowError) {
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseObserver(AbstractView view, String errorMsg, boolean isShowError) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onComplete(){

    }
    @Override
    public void onError(Throwable throwable) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (throwable instanceof ServerException) {
            mView.showErrorMsg(throwable.toString());
        } else if (throwable instanceof HttpException) {
            mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.http_error));
        }else {
            mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.unKnown_error));
            LogHelper.d(throwable.toString());
        }
        if (isShowError) {
            mView.showError();
        }
    }
}
