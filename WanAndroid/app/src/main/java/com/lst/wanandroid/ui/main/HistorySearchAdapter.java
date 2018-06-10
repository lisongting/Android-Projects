package com.lst.wanandroid.ui.main;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lst.wanandroid.R;
import com.lst.wanandroid.core.dao.HistoryData;
import com.lst.wanandroid.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistorySearchAdapter extends BaseQuickAdapter<HistoryData, HistorySearchAdapter.SearchHistoryViewHolder> {

    public HistorySearchAdapter(int layoutResId, @Nullable List<HistoryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(SearchHistoryViewHolder helper, HistoryData historyData) {
        helper.setText(R.id.item_search_history_tv, historyData.getData());
        helper.setTextColor(R.id.item_search_history_tv, CommonUtil.randomColor());

        helper.addOnClickListener(R.id.item_search_history_tv);
    }

    public class SearchHistoryViewHolder extends BaseViewHolder {

        @BindView(R.id.item_search_history_tv)
        TextView mSearchHistoryTv;

        public SearchHistoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
