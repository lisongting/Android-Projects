package com.lst.wanandroid.contract.navigation;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.navigation.NavigationListData;

import java.util.List;

public interface NavigationContract {

    interface View extends AbstractView {

        /**
         * Show navigation list data
         *
         * @param navigationDataList List<NavigationListData>
         */
        void showNavigationListData(List<NavigationListData> navigationDataList);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get navigation list data
         *
         * @param isShowError If show error
         */
        void getNavigationListData(boolean isShowError);
    }

}
