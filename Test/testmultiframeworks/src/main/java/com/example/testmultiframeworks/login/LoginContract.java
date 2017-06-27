package com.example.testmultiframeworks.login;

import com.example.testmultiframeworks.IPresenter;
import com.example.testmultiframeworks.IView;

/**
 * Created by lisongting on 2017/6/25.
 */

public interface LoginContract {
    interface View extends IView<Presenter> {
        void showLoginSuccess();

        void showLoginFailure();
    }

    interface Presenter extends IPresenter{
        void login(String email,String password);
    }
}
