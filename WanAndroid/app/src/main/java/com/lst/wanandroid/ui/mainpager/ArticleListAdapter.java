package com.lst.wanandroid.ui.mainpager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lst.wanandroid.core.bean.main.collect.FeedArticleData;

import java.util.List;

public class ArticleListAdapter extends BaseQuickAdapter<FeedArticleData,KnowledgeHierarchyListViewHolder> {

    private boolean isCollectPage;
    private boolean isSearchPage;
    private boolean isNightMode;

    public ArticleListAdapter(int layoutResId, List<FeedArticleData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, FeedArticleData item) {

    }
}
