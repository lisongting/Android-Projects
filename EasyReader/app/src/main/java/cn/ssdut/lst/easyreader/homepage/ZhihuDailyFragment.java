package cn.ssdut.lst.easyreader.homepage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import cn.ssdut.lst.easyreader.adapter.ZhihuDailyNewsAdapter;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;
import cn.ssdut.lst.easyreader.interfaze.OnRecyclerViewOnClickListener;

/**
 * Created by Administrator on 2017/3/29.
 */

public class ZhihuDailyFragment extends Fragment implements ZhihuDailyContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;
    private FloatingActionButton fab;
    private TabLayout tabLayout;

    private ZhihuDailyNewsAdapter adapter;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private ZhihuDailyContract.Presenter presenter;

    public ZhihuDailyFragment(){}

    public static ZhihuDailyFragment newInstance(){
        return new ZhihuDailyFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceSate){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initViews(view);

        presenter.start();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager =(LinearLayoutManager) recyclerView.getLayoutManager();
                //当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的item的位置
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    //判读是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        Calendar c = Calendar.getInstance();
                        //前一天
                        c.set(mYear, mMonth, --mDay);
                        presenter.loadMore(c.getTimeInMillis());
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
                //隐藏或显示fab
                if (dy > 0) {
                    fab.hide();
                }else{
                    fab.show();
                }
            }
        });

        // 直接将豆瓣精选的fab点击事件放在知乎的部分
        // 因为fab是属于activity的view
        // 按通常的做法，在每个fragment中去设置监听时间会导致先前设置的listener失效
        // 尝试将监听放置到main pager adapter中，这样做会引起fragment中recycler view和fab的监听冲突
        // fab并不能获取到点击事件
        // 根据tab layout的位置选择显示不同的dialog
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Calendar now = Calendar.getInstance();
                    now.set(mYear, mMonth, mDay);
                    DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            Calendar tmp = Calendar.getInstance();
                            tmp.clear();
                            tmp.set(year, monthOfYear, dayOfMonth);
                            presenter.loadPosts(tmp.getTimeInMillis(),true);

                        }
                    },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));

                    dialog.setMaxDate(Calendar.getInstance());
                    Calendar minDate = Calendar.getInstance();
                    //首次上线日期
                    minDate.set(2013, 5, 20);
                    dialog.setMinDate(minDate);
                    dialog.vibrate(false);
                    dialog.show(getActivity().getFragmentManager(), "DatePickerDialog");

                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    ViewPager p = (ViewPager) getActivity().findViewById(R.id.view_pager);
                    MainPagerAdapter ad = (MainPagerAdapter) p.getAdapter();
                    ad.getDoubanFragment().showPickDialog();

                }
            }
        });
        return view;
    }
    @Override
    public void showError() {
        Snackbar.make(fab, R.string.loaded_failed,Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry,new View.OnClickListener(){
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                })
                .show();
    }

    @Override
    public void showLoading() {
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showResult(List<ZhihuDailyNews.Question> list) {
        if (adapter == null) {
            adapter = new ZhihuDailyNewsAdapter(getContext(),list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refresh.setColorSchemeResources(R.color.colorPrimary);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
    }
}
