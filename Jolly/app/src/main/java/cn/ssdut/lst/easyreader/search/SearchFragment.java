package cn.ssdut.lst.easyreader.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.adapter.BookMarksAdapter;
import cn.ssdut.lst.easyreader.bean.DoubanMomentNews;
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;

/**
 * Created by lisongting on 2017/4/30.
 */

public class SearchFragment extends Fragment implements SearchContract.View{

    private SearchContract.Presenter presenter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private BookMarksAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_bookmarks, container, false);

        initViews(view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.loadResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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

        progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.prgressbar);
    }

    @Override
    public void showResult(ArrayList<ZhihuDailyNews.Question> zhihuList, ArrayList<GuokrHandpickNews.result> guokrList, ArrayList<DoubanMomentNews.posts> doubanList, ArrayList<Integer> types) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
