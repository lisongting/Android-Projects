package com.lst.wanandroid.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.utils.CommonUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends AbstractPresenter>
    extends AbstractSimpleFragment implements AbstractView{
    @Inject
    protected T mPresenter;


    @Override
    public void onAttach(Activity activity) {
        //todo:?
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView(){
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (isAdded()) {
            CommonUtil.showSnackMessage(_mActivity,errorMsg);
        }
    }
}
