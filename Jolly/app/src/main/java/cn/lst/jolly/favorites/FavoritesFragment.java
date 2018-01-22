package cn.lst.jolly.favorites;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.lst.jolly.R;
import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.ZhihuDailyNewsQuestion;

/**
 * Created by lisongting on 2018/1/22.
 */

public class FavoritesFragment extends Fragment implements FavoritesContract.View{

    private FavoritesContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private View mEmptyView;
    private FavoritesAdapter mAdapter;

    public FavoritesFragment(){

    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        initViews(view);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadFavorites();
            }
        });
        return view;
    }

    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.loadFavorites();
    }

    @Override
    public void setPresenter(FavoritesContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mEmptyView = view.findViewById(R.id.empty_view);
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
//        mRefreshLayout
    }

    @Override
    public void showFavorites(List<ZhihuDailyNewsQuestion> zhihuList, List<DoubanMomentNewsPosts> doubanList, List<GuokrHandpickNewsResult> guokrList) {

    }
}
