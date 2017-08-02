package com.paperfish.espresso.mvp.packages;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.paperfish.espresso.data.Package;

import java.util.List;

/**
 * Created by lisongting on 2017/7/31.
 */

public class PackageFragment extends Fragment implements PackagesContract.View{

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private SwipeRefreshLayout refreshLayout;
    private PackagesAdapter adapter;
    private PackagesContract.Presenter presenter;

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(PackagesContract.Presenter presenter) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showEmptyView(boolean toShow) {

    }

    @Override
    public void showPackages(@NonNull List<Package> list) {

    }

    @Override
    public void shareTo(@NonNull Package pack) {

    }

    @Override
    public void showPackageRemovedMsg(String packageName) {

    }

    @Override
    public void copyPackageNumber() {

    }

    @Override
    public void showNetworkError() {

    }
}
