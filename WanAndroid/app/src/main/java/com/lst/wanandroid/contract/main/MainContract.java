package com.lst.wanandroid.contract.main;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;

public interface MainContract {
    interface View extends AbstractView{
        void showSwitchProject();

        void showSwitchNavigation();
    }

    interface Presenter extends AbstractPresenter<View>{
        void showCurrentPage(int page);

        void setNightModeState(boolean b);
    }
}
