package com.paperfish.espresso.mvp.packages;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.paperfish.espresso.mvp.companies.CompaniesFragment;

/**
 * Created by lisongting on 2017/8/3.
 */

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private PackagesFragment packagesFragment;
    private PackagesPresenter packagesPresenter;
    private CompaniesFragment companiesFragment;
    private static final String KEY_NAV_ITEM = "CURRENT_NAV_ITEM";
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private int selectedNavItem = 0;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }
}
