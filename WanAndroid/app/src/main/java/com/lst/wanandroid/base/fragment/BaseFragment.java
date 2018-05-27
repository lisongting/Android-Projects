package com.lst.wanandroid.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends AbstractPresenter>
    extends AbstractSimpleFragment implements AbstractView{
    @Inject
    protected T mPresenter;


    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
