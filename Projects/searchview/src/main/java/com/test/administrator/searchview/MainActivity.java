package com.test.administrator.searchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;
    private String[] mstrings = {"aaaaaa","bbbbbbb","ccccccccc"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        searchView = (SearchView)findViewById(R.id.searchView);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mstrings);
        listView.setAdapter(adapter);

        //设置listView启用过滤
        listView.setTextFilterEnabled(true);
        searchView.setIconifiedByDefault(true);//设置该searchView是否默认是否自动缩小为图标
        searchView.setSubmitButtonEnabled(true);//显示搜索按钮
        searchView.setQueryHint("查找");
        //用户输入字符时触发该方法
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText){
                if(TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }else{
                    listView.setFilterText(newText);
                }
                return true;
            }

            //用户单击搜索时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query){
                Toast.makeText(MainActivity.this,"您选择的内容是"+query,Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }
}
