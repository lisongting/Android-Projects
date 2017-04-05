package cn.ssdut.lst.swiperefreshlayouttest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用SwipeRefreshLayout实现下拉刷新和上拉加载,
 * 并且使用了layoutAnimation来设置RecyclerView中的每一个条目的进场动画
 */
public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private List<String> mList ;
    private RecyclerViewAdapter mAdapter;
    private int lastVisibleItem;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeLayout.setColorSchemeColors(Color.BLUE,Color.GREEN);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mSwipeLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAdapter.addData(0);
                        Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void initView() {
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mList = new ArrayList<>();
        for(int i=0;i<25;i++) {
            mList.add("item"+i);
        }
        mAdapter = new RecyclerViewAdapter(MainActivity.this, mList);
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                Toast.makeText(MainActivity.this, "点击位置："+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View item, int position) {
                Toast.makeText(MainActivity.this, "长按位置："+position, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        final LinearLayoutManager layoutManger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManger);


        //设置滚动监听器
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManger instanceof LinearLayoutManager) {
                    lastVisibleItem = layoutManger.findLastVisibleItemPosition();
                    Log.i("tag", "lastVisibleItem:" + lastVisibleItem);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == mAdapter.getItemCount()) {

                    mAdapter.changeLoadingState(mAdapter.STATUS_LOADING);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadData(5);
                            mAdapter.changeLoadingState(mAdapter.STATUS_IDLE);
                            Toast.makeText(MainActivity.this, "加载了5条数据", Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);

                }
            }
        });
    }


}
