package cn.ssdut.lst.settingactivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    @TargetApi(Build.VERSION_CODES.N)
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        //在其他Activity中获取设置页面设置好的选项值
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Toast.makeText(this, "name:"+sp.getString("name","unknown"), Toast.LENGTH_SHORT).show();
    }

    public void onClickButton(View view) {
        Preference preferenceFragment = new Preference();
        FragmentManager fn = getFragmentManager();
        FragmentTransaction transaction = fn.beginTransaction();
        transaction.replace(R.id.id_frameLayout, preferenceFragment);
        transaction.commit();
    }

    public static class Preference extends PreferenceFragment {

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public  void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            String name = ((EditTextPreference) findPreference("name")).getText();
            final EditTextPreference editTextPreference = (EditTextPreference) findPreference("name");
            final ListPreference listPreference = (ListPreference) findPreference("sex");
            final CheckBoxPreference cheboxPreference = (CheckBoxPreference) findPreference("push");
            final SwitchPreference switchPreference = (SwitchPreference) findPreference("autoRun");

            //使用SharedPreference来存储设置的值并进行恢复
            SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
            editTextPreference.setSummary(sp.getString("name","default"));
            listPreference.setSummary(sp.getString("sex","default"));

            editTextPreference.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                    Toast.makeText(getContext(), "new value:"+newValue, Toast.LENGTH_SHORT).show();
                    editTextPreference.setSummary((String) newValue);
                    return true;
                }
            });

            listPreference.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                    Toast.makeText(getContext(), "new value:"+newValue, Toast.LENGTH_SHORT).show();
                    listPreference.setSummary((CharSequence) newValue);
                    return false;
                }
            });

            cheboxPreference.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                    Toast.makeText(getContext(), "new value:"+newValue, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            switchPreference.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                    Toast.makeText(getContext(), "new value:"+newValue, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        }
    }
}

