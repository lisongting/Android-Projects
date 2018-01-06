package cn.lst.jolly.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.lst.jolly.R;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID =
            "KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID";
    public static final String ACTION_FAVORITES = "cn.lst.jolly.favorites";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
