package com.paperfish.streammediatest;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lisongting on 2017/12/5.
 */

public class CustomVideoActivity extends AppCompatActivity {

    private CustomVideoFragment videoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate()");

        if (savedInstanceState == null) {
            videoFragment = new CustomVideoFragment();
        } else {
            videoFragment = (CustomVideoFragment) getSupportFragmentManager().findFragmentByTag("videoFragment");
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.layout_container);
        }
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//            Toast.makeText(this, "landScape", Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.fragment_video_full_screen);
//        }

        if (!videoFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add( R.id.container,videoFragment,"videoFragment")
                    .commit();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume()");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        log("onConfigurationChanged()");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        log("onRestoreInstanceState()");
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log("onSaveInstanceState()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy()");
    }

    private void log(String s) {
        Log.i("tag", "Activity -- "+s);
    }
}
