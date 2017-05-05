package cn.ssdut.lst.easyreader.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ssdut.lst.easyreader.R;

/**
 * Created by lisongting on 2017/5/5.
 */

public class SearchActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        SearchFragment fragment = SearchFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        //这里在创建对象的时候，就把fragment与SeachPresenter绑定了
        new SearchPresenter(this, fragment);
    }
}
