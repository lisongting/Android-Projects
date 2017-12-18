package cn.iscas.xlab.fragmentlifecycle;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lisongting on 2017/12/10.
 */

public class Activity2 extends AppCompatActivity {
    FragmentManager fragmentManager;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        frameLayout = (FrameLayout) findViewById(R.id.container);

        fragmentManager = getSupportFragmentManager();

//        fragmentManager.beginTransaction()
//                .setAllowOptimization(false)
//                .add(R.id.container,SimpleFragment1.getInstance("fragment") , "fragment")
//                .commit();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container,SimpleFragment1.getInstance("fragment") , "fragment")
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        log("onStart");
        super.onStart();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        log("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        log("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        log("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log("onSaveInstanceState");
        super.onSaveInstanceState(outState);

    }

    private void log(String s) {
        Log.i("tag", "Activity2 -- " + s);
    }
}
