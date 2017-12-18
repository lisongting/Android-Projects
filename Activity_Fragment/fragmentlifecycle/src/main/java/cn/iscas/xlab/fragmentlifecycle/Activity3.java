package cn.iscas.xlab.fragmentlifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试BottomNavigationView下fragment切换时Activity与Fragment的生命周期
 */
public class Activity3 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FrameLayout frameLayout;

    List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log("onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation );

        fragments = new ArrayList<>();

        fragmentManager = getSupportFragmentManager();
        fragments.add(SimpleFragment1.getInstance("tab1"));
        fragments.add(SimpleFragment2.getInstance("tab2"));
        fragments.add(SimpleFragment3.getInstance("tab3"));

        fragmentManager.beginTransaction()
                .add(R.id.container,fragments.get(0) , "tab1")
                .add(R.id.container,fragments.get(1) , "tab2")
                .add(R.id.container,fragments.get(2) , "tab3")
                .commit();

    }


    @Override
    protected void onStart() {
        log("onStart()");
        super.onStart();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        fragmentManager.beginTransaction()
                                .hide(fragments.get(1))
                                .hide(fragments.get(2))
                                .show(fragments.get(0))
                                .commit();
                        break;
                    case R.id.tab2:
                        fragmentManager.beginTransaction()
                                .hide(fragments.get(0))
                                .hide(fragments.get(2))
                                .show(fragments.get(1))
                                .commit();
                        break;
                    case R.id.tab3:
                        fragmentManager.beginTransaction()
                                .hide(fragments.get(1))
                                .hide(fragments.get(0))
                                .show(fragments.get(2))
                                .commit();
                        break;
                    default:
                        break;

                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.tab1);
    }

    @Override
    protected void onResume() {
        log("onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        log("onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        log("onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        log("onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log("onSaveInstanceState()");
        outState.putString("key", "test");
        super.onSaveInstanceState(outState);

    }

    private void log(String s) {
        Log.i("tag", "Activity  -- " + s);
    }


}
