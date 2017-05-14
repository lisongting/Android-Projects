package cn.ssdut.lst.easyreader.about;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.ssdut.lst.easyreader.R;

/**
 * Created by lisongting on 2017/5/9.
 */

public class AboutPreferenceFragment extends PreferenceFragmentCompat implements AboutContract.View{
    private Toolbar toolbar;
    private AboutContract.Presenter presenter;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.about_preference_fragment);

        initViews(getView());

        findPreference("rate").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.rate();
                return false;
            }
        });

        findPreference("open_source_license").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.openLicense();
                return false;
            }
        });

        findPreference("follow_me_on_github").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.followOnGithub();
                return false;
            }
        });

        findPreference("follow_me_on_zhihu").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.followOnZhihu();
                return false;
            }
        });

        findPreference("feedback").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.feedback();
                return false;
            }
        });

        findPreference("coffee").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.donate();
                return false;
            }
        });

        findPreference("author").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.showEasterEgg();
                return false;
            }
        });
    }

    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public void showRateError() {
        Snackbar.make(toolbar, R.string.no_app_store_found,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFeedbackError() {
        Snackbar.make(toolbar, R.string.no_mail_app,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showBrowserNotFoundError() {
        Snackbar.make(toolbar, R.string.no_browser_found,Snackbar.LENGTH_SHORT).show();
    }

}
