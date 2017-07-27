package com.paperfish.espresso.mvp.addpackage;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.paperfish.espresso.R;
import com.paperfish.espresso.data.source.CompaniesRepository;
import com.paperfish.espresso.data.source.PackagesRepository;
import com.paperfish.espresso.data.source.local.CompaniesLocalDataSource;
import com.paperfish.espresso.data.source.local.PackagesLocalDataSource;
import com.paperfish.espresso.data.source.remote.PackagesRemoteDataSource;

/**
 * Created by lisongting on 2017/7/27.
 */

public class AddPackageActivity extends AppCompatActivity {

    private AddPackageFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("navigation_bat_tint", true)) {
            fragment = (AddPackageFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AddPackageFragment");
        } else {
            fragment = AddPackageFragment.getInstance();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.view_pager, fragment, "AddPackageFragment")
                    .commit();
        }

        new AddPackagePresenter(PackagesRepository.getInstance(PackagesRemoteDataSource.getInstance()
                , PackagesLocalDataSource.getInstance())
                , CompaniesRepository.getInstance(CompaniesLocalDataSource.getInstance())
                , fragment);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"AddPackageFragment", fragment);
    }
}
