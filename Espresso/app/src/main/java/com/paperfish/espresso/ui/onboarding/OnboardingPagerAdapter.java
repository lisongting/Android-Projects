package com.paperfish.espresso.ui.onboarding;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lisongting on 2017/7/14.
 */

public class OnboardingPagerAdapter extends FragmentPagerAdapter {

    private final int pageCount =3;

    public OnboardingPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return OnboardingFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return pageCount;
    }

}
