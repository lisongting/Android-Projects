package com.test.administrator.uitest;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;

@TargetApi(21)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Display display = getWindowManager().getDefaultDisplay();
        Log.i("tag", "rotation:" + display.getRotation());
        Log.i("tag", "name:" + display.getName());
        log.i("tag", Surface.ROTATION_0)
        super.onSaveInstanceState(outState);
    }
}
