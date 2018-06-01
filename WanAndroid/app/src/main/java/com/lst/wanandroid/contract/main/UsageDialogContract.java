package com.lst.wanandroid.contract.main;


import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.main.search.UsefulSiteData;

import java.util.List;

public interface UsageDialogContract {

    interface View extends AbstractView {

        /**
         * Show useful sites
         *
         * @param usefulSiteDataList List<UsefulSiteData>
         */
        void showUsefulSites(List<UsefulSiteData> usefulSiteDataList);
    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 常用网站
         */
        void getUsefulSites();
    }

}
