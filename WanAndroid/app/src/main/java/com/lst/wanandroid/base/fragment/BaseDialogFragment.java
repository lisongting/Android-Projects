package com.lst.wanandroid.base.fragment;

import android.content.Context;
import android.os.Bundle;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.utils.CommonUtil;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseDialogFragment<T extends AbstractPresenter>
        extends AbstractSimpleDialogFragment implements AbstractView{

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (getActivity() != null) {
            CommonUtil.showSnackMessage(getActivity(),errorMsg);
        }
    }


}
