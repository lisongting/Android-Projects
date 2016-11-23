package com.test.administrator.tabhost;

import android.app.TabActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabhost = getTabHost();
        TabHost.TabSpec tab1 = tabhost.newTabSpec("tab1").setIndicator("已接电话").setContent(R.id.tab01);
        TabHost.TabSpec tab2 = tabhost.newTabSpec("tab2").setIndicator("呼出电话").setContent(R.id.tab02);
        TabHost.TabSpec tab3 = tabhost.newTabSpec("tab3").setIndicator("未接电话").setContent(R.id.tab03);
        tabhost.addTab(tab1);
        tabhost.addTab(tab2);
        tabhost.addTab(tab3);
    }
}
