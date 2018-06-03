package com.lst.wanandroid.base.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lst.wanandroid.app.WanAndroidApp;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractSimpleDialogFragment extends DialogFragment {
    private Unbinder unbinder;
    private View mRootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mRootView = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initEventAndData();
        return mRootView;
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        RefWatcher refWatcher = WanAndroidApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}
