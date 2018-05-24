package com.lst.wanandroid.core.event;

public class LoginEvent {
    private boolean isLogin;

    public boolean isLogin(){
        return isLogin;
    }

    public void setLogin(boolean login){
        this.isLogin = login;
    }

    public LoginEvent(boolean b) {
        this.isLogin = b;
    }
}
