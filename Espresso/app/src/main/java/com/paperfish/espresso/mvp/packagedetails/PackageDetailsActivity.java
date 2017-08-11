package com.paperfish.espresso.mvp.packagedetails;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.paperfish.espresso.R;
import com.paperfish.espresso.data.source.PackagesRepository;
import com.paperfish.espresso.data.source.local.PackagesLocalDataSource;
import com.paperfish.espresso.data.source.remote.PackagesRemoteDataSource;

/**
 * Created by lisongting on 2017/8/6.
 */

public class PackageDetailsActivity extends AppCompatActivity {
    private PackageDetailsFragment fragment;

    public static final String PACKAGE_ID = "PACKAGE_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("navigation_bar_tint", true)) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        if (savedInstanceState != null) {
            //从fragmentManager中拿到保存着的packageDetailsFragment
            fragment = (PackageDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PackageDetailsFragment");
        } else {
            fragment = PackageDetailsFragment.getInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_pager, fragment)
                .commit();

        new PackageDetailsPresenter(getIntent().getStringExtra(PACKAGE_ID),
                PackagesRepository.getInstance(
                        PackagesRemoteDataSource.getInstance(),
                        PackagesLocalDataSource.getInstance()
                ),fragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "PackageDetailsFragment", fragment);
    }
}
