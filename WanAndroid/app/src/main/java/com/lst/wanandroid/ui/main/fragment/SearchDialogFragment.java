package com.lst.wanandroid.ui.main.fragment;

import com.lst.wanandroid.base.fragment.BaseDialogFragment;
import com.lst.wanandroid.contract.main.SearchContract;
import com.lst.wanandroid.core.bean.main.search.TopSearchData;
import com.lst.wanandroid.core.dao.HistoryData;

import java.util.List;

public class SearchDialogFragment extends BaseDialogFragment<SearchPresenter>
    implements SearchContract.View{
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {

    }

    @Override
    public void showTopSearchData(List<TopSearchData> topSearchDataList) {

    }

    @Override
    public void judgeToTheSearchListActivity() {

    }

    @Override
    public void useNightMode(boolean isNightMode) {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }
}
