package cn.ssdut.lst.recyclerviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> mDatas;
    private RecyclerViewAdapter<RecyclerView.ViewHolder> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initViews();
        mAdapter = new RecyclerViewAdapter<RecyclerView.ViewHolder>(this, mDatas);
        recyclerView.setAdapter(mAdapter);

        //设置recyclerView的布局,最后一个boolean参数，如果为true则每个元素倒着布置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //s设置RecyclerView的item之间的分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        //通过这种方法可以添加插入动画
        //recyclerView.setItemAnimator();

        //为mAdapter设置点击监听器
        mAdapter.setmOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Toast.makeText(MainActivity.this, "点击的位置是"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "长按的位置是"+position, Toast.LENGTH_SHORT).show();
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
            case R.id.action_add:
                mAdapter.addData(1);

                break;
            case R.id.action_delete:
                mAdapter.deleteData(1);

                break;
            case R.id.action_gridview:
                //GridLayoutManager(Context context, int spanCount) ，第二个参数是列数
                recyclerView.setLayoutManager(new GridLayoutManager(this,3));
                break;
            case R.id.action_listview:
                //LinearLayoutManager的布局默认为vertical
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            //交错式
            case R.id.action_staggered:
                Intent t = new Intent(this, StaggerGridActivity.class);
                startActivity(t);
                break;
            case R.id.horizontal_gridview:
                //如果是纵向的，StaggeredGridLayoutManager的第一个参数是列数
                //如果是横向的，则第二个参数是行数
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                break;
            default:
                break;

        }
        return true;
    }

}
