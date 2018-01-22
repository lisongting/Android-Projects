package cn.lst.jolly.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import cn.lst.jolly.R;
import cn.lst.jolly.favorites.FavoritesFragment;
import cn.lst.jolly.timeline.TimelineFragment;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID =
            "KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID";
    public static final String ACTION_FAVORITES = "cn.lst.jolly.favorites";

    private TimelineFragment mTimelineFragment ;
    private InfoFragment mInfoFragment;
    private FavoritesFragment mFavoritesFragment;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initViews();

//        initFragments(savedInstanceState);

//        new FavoritesPresenter()
    }
}
