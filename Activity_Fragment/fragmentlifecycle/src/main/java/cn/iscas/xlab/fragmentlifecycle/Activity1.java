package cn.iscas.xlab.fragmentlifecycle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lisongting on 2017/11/20.
 */

public class Activity1 extends AppCompatActivity {
    FragmentManager fragmentManager;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
        setContentView(R.layout.activity_simple);
        frameLayout = (FrameLayout) findViewById(R.id.container);


        fragmentManager = getSupportFragmentManager();

//        fragmentManager.beginTransaction()
//                .setAllowOptimization(false)
//                .add(R.id.container,SimpleFragment1.getInstance("fragment") , "fragment")
//                .commit();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, SimpleFragment1.getInstance("fragment"), "fragment")
                    .commit();
            log("savedInstance is null");
        } else {
            log("savedInstance is NOT null");
        }



    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        log("onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log("onSaveInstanceState");

    }

    private void log(String s) {
        Log.i("tag", "Activity1 -- " + s);
    }
}
