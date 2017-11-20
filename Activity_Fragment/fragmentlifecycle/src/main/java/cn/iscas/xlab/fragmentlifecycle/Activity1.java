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
        log("onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
        frameLayout = (FrameLayout) findViewById(R.id.container);


        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .setAllowOptimization(false)
                .add(R.id.container,SimpleFragment1.getInstance("fragment") , "fragment")
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart()");

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
        log("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy()");
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
