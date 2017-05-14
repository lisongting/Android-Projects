package cn.ssdut.lst.easyreader.innerbrowser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.ssdut.lst.easyreader.R;

/**
 * Created by lisongting on 2017/5/9.
 */

public class InnerBrowserActivity extends AppCompatActivity{

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, InnerBrowserFragment.getInstance()).commit();

    }
}
