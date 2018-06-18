package com.lst.wanandroid.contract.hierarchy;

import com.lst.wanandroid.base.AbstractView;
import com.lst.wanandroid.base.presenter.AbstractPresenter;
import com.lst.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;

import java.util.List;

public interface KnowledgeHierarchyContract {
    interface View extends AbstractView {
        void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList);
    }

    interface Presenter extends AbstractPresenter<View>{
        void getKnowledgeHierarchyData(boolean isShowError);
    }
}
