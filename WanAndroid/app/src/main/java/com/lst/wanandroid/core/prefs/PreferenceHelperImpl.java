package com.lst.wanandroid.core.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.lst.wanandroid.app.WanAndroidApp;
import com.lst.wanandroid.app.Constants;

import javax.inject.Inject;

public class PreferenceHelperImpl implements PreferenceHelper {
    private static final String MY_SHARED_PREFS = "my_shared_preference";
    private final SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    @Inject
    public PreferenceHelperImpl(){
        mPreferences = WanAndroidApp.getInstance().getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);
        editor = mPreferences.edit();
    }

    @Override
    public void setLoginAccount(String account) {
        editor.putString(Constants.ACCOUNT, account).apply();
    }

    @Override
    public void setLoginPassword(String password) {
        editor.putString(Constants.PASSWORD, password).apply();
    }

    @Override
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }

    @Override
    public String getLoginPassword() {
        return mPreferences.getString(Constants.PASSWORD, "");
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        editor.putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS,false);
    }

    @Override
    public void setCookie(String domain, String cookie) {
        editor.putString(domain,cookie).apply();
    }

    @Override
    public String getCookie(String domain) {
        return mPreferences.getString(Constants.COOKIE,"");
    }

    @Override
    public void setCurrentPage(int position) {
        editor.putInt(Constants.CURRENT_PAGE, position).apply();
    }

    @Override
    public int getCurrentPage() {
        return mPreferences.getInt(Constants.CURRENT_PAGE, 0);
    }

    @Override
    public void setProjectCurrentPage(int position) {
        editor.putInt(Constants.PROJECT_CURRENT_PAGE, position).apply();
    }

    @Override
    public int getProjectCurrentPage() {
        return mPreferences.getInt(Constants.PROJECT_CURRENT_PAGE, 0);
    }

    @Override
    public boolean getAutoCacheState() {
        return mPreferences.getBoolean(Constants.AUTO_CACHE_STATE,true);
    }

    @Override
    public boolean getNoImageState(){
        return mPreferences.getBoolean(Constants.NO_IMAGE_STATE, false);
    }

    @Override
    public boolean getNightModeState() {
        return mPreferences.getBoolean(Constants.NIGHT_MODE_STATE,false);
    }

    @Override
    public void setNightModeState(boolean b) {
        editor.putBoolean(Constants.NO_IMAGE_STATE,b).apply();
    }

    @Override
    public void setNoImageState(boolean b) {
        editor.putBoolean(Constants.NO_IMAGE_STATE, b).apply();
    }

    @Override
    public void setAutoCacheState(boolean b) {
        editor.putBoolean(Constants.AUTO_CACHE_STATE,b).apply();
    }
}
