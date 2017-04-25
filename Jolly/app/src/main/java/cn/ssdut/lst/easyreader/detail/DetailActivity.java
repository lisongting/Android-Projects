package cn.ssdut.lst.easyreader.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.bean.BeanType;

/**
 * Created by lisongting on 2017/4/24.
 */

public class DetailActivity extends AppCompatActivity {
    private DetailFragment fragment;

    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.frame);

        if (savedInstanceSate != null) {
            fragment = (DetailFragment) getSupportFragmentManager().getFragment(savedInstanceSate, "detailFragment");
        } else {
            fragment = new DetailFragment();
            //将framLayout替换为Fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

        //这个Activity是通过startIntent启动起来的，这个Intent中携带着type,title,coverUrl等相关信息
        Intent intent = getIntent();
        DetailPresenter presenter = new DetailPresenter(DetailActivity.this, fragment);
        presenter.setType((BeanType) intent.getSerializableExtra("type"));
        presenter.setId(intent.getIntExtra("id", 0));
        presenter.setTitle(intent.getStringExtra("title"));
        presenter.setCoverUrl(intent.getStringExtra("coverUrl"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "detailFragment", fragment);
        }
    }
}
