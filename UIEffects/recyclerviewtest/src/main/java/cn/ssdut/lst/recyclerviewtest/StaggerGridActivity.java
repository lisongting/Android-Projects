package cn.ssdut.lst.recyclerviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */

public class StaggerGridActivity extends Activity {
    private RecyclerView recyclerView;
    private List<String> mDatas;
    private StaggerGridAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initViews();
        mAdapter = new StaggerGridAdapter(this, mDatas);
        recyclerView.setAdapter(mAdapter);

        //设置recyclerView的布局
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        //设置点击监听器
        mAdapter.setmOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(StaggerGridActivity.this, "点击的位置是"+position, Toast.LENGTH_SHORT).show();
            }

            //当长按时，移除当前的item
            @Override
            public void onItemLongClick(View v, int position) {
                mAdapter.deleteData(position);

            }
        });
    }


    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for(int i='A';i<='Z';i++) {
            mDatas.add(""+(char)i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        //返回true才会显示菜单，否则不会显示菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


        }
        return true;
    }
}
