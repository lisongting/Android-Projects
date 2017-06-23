package cn.ssdut.lst.easyreader.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.adapter.BookmarksAdapter;
import cn.ssdut.lst.easyreader.bean.BeanType;
import cn.ssdut.lst.easyreader.bean.DoubanMomentNews;
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;
import cn.ssdut.lst.easyreader.interfaze.OnRecyclerViewOnClickListener;

/**
 * Created by lisongting on 2017/4/30.
 */

public class SearchFragment extends Fragment implements SearchContract.View{

    private SearchContract.Presenter presenter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private BookmarksAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_bookmarks, container, false);

        initViews(view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("tag", "searchView -- onQueryTextSubmit:" + query);
                presenter.loadResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("tag", "searchView -- onQueryTextChange:" + newText);
                presenter.loadResult(newText);
                return true;
            }
        });
        return view;
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(android.view.View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((SearchActivity) (getActivity())).setSupportActionBar(toolbar);
        ((SearchActivity) (getActivity())).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setIconified(false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);


        progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void showResult(ArrayList<ZhihuDailyNews.Question> zhihuList, ArrayList<GuokrHandpickNews.result> guokrList, ArrayList<DoubanMomentNews.posts> doubanList, ArrayList<Integer> types) {

        adapter = new BookmarksAdapter(getActivity(), zhihuList, guokrList, doubanList, types);
        adapter.setItemListener(new OnRecyclerViewOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int type = recyclerView.findViewHolderForLayoutPosition(position).getItemViewType();
                if (type == BookmarksAdapter.TYPE_ZHIHU_NORMAL) {
                    presenter.startReading(BeanType.TYPE_ZHIHU,position);
                } else if (type == BookmarksAdapter.TYPE_GUOKR_NORMAL) {
                    presenter.startReading(BeanType.TYPE_GUOKR, position);
                } else if (type == BookmarksAdapter.TYPE_DOUBAN_NORMAL) {
                    presenter.startReading(BeanType.TYPE_DOUBAN,position);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
