package cn.ssdut.lst.easyreader.bookmarks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import cn.ssdut.lst.easyreader.search.SearchActivity;

/**
 * Created by lisongting on 2017/5/12.
 */

public class BookmarksFragment extends Fragment implements BookmarksContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private BookmarksAdapter adapter;
    private BookmarksContract.Presenter presenter;

    public BookmarksFragment() {

    }

    public static BookmarksFragment newInstance() {
        return new BookmarksFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, viewGroup,false);

        initViews(view);

        setHasOptionsMenu(true);

        presenter.loadResults(false);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadResults(true);
            }
        });
        return view;

    }
    @Override
    public void setPresenter(BookmarksContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bookmarks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        } else if (id == R.id.action_feel_lucky) {
            presenter.feelLucky();
        }
        return true;
    }

    @Override
    public void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList, ArrayList<GuokrHandpickNews.result> guokrList, ArrayList<DoubanMomentNews.posts> doubanList, ArrayList<Integer> types) {
        if (adapter == null) {

            adapter = new BookmarksAdapter(getActivity(), zhihuList, guokrList, doubanList, types);
            adapter.setItemListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    int type = recyclerView.findViewHolderForLayoutPosition(position).getItemViewType();
                    if (type == BookmarksAdapter.TYPE_ZHIHU_NORMAL) {
                        presenter.startReading(BeanType.TYPE_ZHIHU, position);
                    } else if (type == BookmarksAdapter.TYPE_GUOKR_NORMAL) {
                        presenter.startReading(BeanType.TYPE_GUOKR, position);
                    } else if (type == BookmarksAdapter.TYPE_DOUBAN_NORMAL) {
                        presenter.startReading(BeanType.TYPE_DOUBAN, position);
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyDataChanged() {
        presenter.loadResults(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }
}
