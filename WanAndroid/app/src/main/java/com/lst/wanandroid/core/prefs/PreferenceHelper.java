package com.lst.wanandroid.core.prefs;

public interface PreferenceHelper {
    void setLoginAccount(String account);

    void setLoginPassword(String password);

    String getLoginAccount();

    String getLoginPassword();

    void setLoginStatus(boolean isLogin);

    boolean getLoginStatus();

    void setCookie(String domain,String cookie);

    String getCookie(String domain);

    void setCurrentPage(int position);

    int getCurrentPage();

    void setProjectCurrentPage(int position);

    int getProjectCurrentPage();

    boolean getAutoCacheState();

    boolean getNoImageState();

    boolean getNightModeState();

    void setNightModeState(boolean b);

    void setNoImageState(boolean b);

    void setAutoCacheState(boolean b);
}
