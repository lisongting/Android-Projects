package com.lst.wanandroid.contract.main;


import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.login.LoginData;

public interface RegisterContract {

    interface View extends AbstractView {

        /**
         * Show register data
         *
         * @param loginData LoginData
         */
        void showRegisterData(LoginData loginData);
    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 注册
         * http://www.wanandroid.com/user/register
         *
         * @param username user name
         * @param password password
         * @param rePassword re password
         */
        void getRegisterData(String username, String password, String rePassword);
    }
}
