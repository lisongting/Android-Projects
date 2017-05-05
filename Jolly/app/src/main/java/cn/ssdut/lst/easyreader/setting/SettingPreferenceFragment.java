package cn.ssdut.lst.easyreader.setting;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.ssdut.lst.easyreader.R;

/**
 * Created by lisongting on 2017/5/5.
 */

public class SettingPreferenceFragment extends PreferenceFragmentCompat
        implements SettingContract.View{

    private SettingContract.Presenter presenter;
    private Toolbar toolbar;
    private Preference timePreference;

    public SettingPreferenceFragment() {

    }

    public static SettingPreferenceFragment newInstance() {
        return new SettingPreferenceFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preference_fragment);

        initViews(getView());

        findPreference("no_picture_mode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.setNoPictureMode(preference);
                return false;
            }
        });

        findPreference("in_app_browser").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.setInAppBrowser(preference);
                return false;
            }
        });

        findPreference("clear_glide_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.cleanGlideCache();
                return false;
            }
        });

        timePreference = findPreference("time_of_saving_articles");

        timePreference.setSummary(presenter.getTimeSummary());

        timePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                presenter.setTimeOfSavingArticles(preference, newValue);
                timePreference.setSummary(presenter.getTimeSummary());
                return false;
            }
        });
    }

    public void onResume() {
        super.onResume();
        presenter.start();
    }


    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public void showCleanGlideCacheDone() {
        Snackbar.make(toolbar, R.string.clear_image_cache_successfully, Snackbar.LENGTH_SHORT).show();

    }
}
