package com.test.administrator.actionbartest;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar  = getActionBar();
    }

    public void showActionBar(View view){
        actionBar.show();
    }

    public void hideActionBar(View view){
        actionBar.hide();
    }
}
