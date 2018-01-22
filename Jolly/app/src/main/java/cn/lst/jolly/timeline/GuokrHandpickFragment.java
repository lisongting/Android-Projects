package cn.lst.jolly.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.lst.jolly.R;
import cn.lst.jolly.data.ContentType;
import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.PostType;
import cn.lst.jolly.details.DetailsActivity;
import cn.lst.jolly.service.CacheService;

/**
 * Created by lisongting on 2018/1/16.
 */

public class GuokrHandpickFragment extends Fragment implements GuokrHandpickContract.View {
    private GuokrHandpickContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private View mEmptyView;
    private GuokrHandpickNewsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mOffset = 0;
    private boolean mIsFirstLoad;
    private int mListSize;

    public GuokrHandpickFragment() {
    }

    public static GuokrHandpickFragment newInstance() {
        return new GuokrHandpickFragment();
    }

    @Override
    public void setPresenter(GuokrHandpickContract.Presenter presenter) {
        this.presenter = presenter;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline_page, parent, false);
        initViews(v);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load(true, true, 0, 20);
                mOffset = 0;
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mLayoutManager.findLastCompletelyVisibleItemPosition() == mListSize - 1) {
                    loadMore();
                }
            }
        });
        return v;
    }

    public void onResume() {
        super.onResume();
        presenter.start();
        setLoadingIndicator(mIsFirstLoad);
        if (mIsFirstLoad) {
            presenter.load(true, false, mOffset, 20);
            mIsFirstLoad = false;
        } else {
            presenter.load(false,false,mOffset,20);
        }
    }

    @Override
    public void initViews(View view) {
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mEmptyView = view.findViewById(R.id.empty_view);
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showResult(@NonNull final List<GuokrHandpickNewsResult> list) {
        mOffset = list.size();
        if (mAdapter == null) {
            mAdapter = new GuokrHandpickNewsAdapter(list, getContext());
            mAdapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_ID, list.get(position).getId());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_TITLE, ContentType.TYPE_GUOKR_HANDPICK);
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_IS_FAVORITE, list.get(position).isFavorite());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_TITLE, list.get(position).getTitle());
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(list);
        }
        mListSize = list.size();
        mEmptyView.setVisibility(list.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        for (GuokrHandpickNewsResult item : list) {
            Intent intent = new Intent(CacheService.BROADCAST_FILTER_ACTION);
            intent.putExtra(CacheService.FLAG_ID, item.getId());
            intent.putExtra(CacheService.FLAG_TYPE, PostType.TYPE_GUOKR);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        }

    }

    public void loadMore() {
        presenter.load(true, false, mOffset, 20);
    }
}
