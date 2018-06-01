package com.lst.wanandroid.contract.hierarchy;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;

public interface KnowledgeHierarchyDetailContract {

    interface View extends AbstractView {
        void showSwitchProject();

        void showSwitchNavigation();
    }

    interface Presenter extends AbstractPresenter<View>{

    }
}
