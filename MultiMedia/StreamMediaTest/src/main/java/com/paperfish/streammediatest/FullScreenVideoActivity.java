package com.paperfish.streammediatest;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lisongting on 2017/12/6.
 */

public class FullScreenVideoActivity extends AppCompatActivity{

    private SurfaceView surfaceView;
    private RelativeLayout topBar,bottomBar;
    private ImageButton btBack;
    private ImageButton btPlayState;
    private ImageButton btFullScreen;
    private boolean isMenuOpened ;
    private boolean isPlaying;
    private ImageButton btVideoList;
    private ImageView ivAnim;
    private boolean isLoading;
    private Animation waitAnimation;
    
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private String[] infoList = {"RGB图像","深度图像"};
    private List<Map<String,String>> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_full_screen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        surfaceView = (SurfaceView) findViewById(R.id.sv_video);
        topBar = (RelativeLayout) findViewById(R.id.top_bar);
        bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar);
        btBack = (ImageButton) findViewById(R.id.ib_back);
        btPlayState = (ImageButton) findViewById(R.id.ib_play_state);
        btFullScreen = (ImageButton) findViewById(R.id.ib_screen_state);
        btVideoList = (ImageButton) findViewById(R.id.ib_list);
        listView = (ListView) findViewById(R.id.list_view);
        ivAnim = (ImageView) findViewById(R.id.iv_anim);


        initView();
    }

    private void initView() {
        listData = new ArrayList<>();
        for(int i=0;i<infoList.length;i++) {
            Map<String, String> map = new HashMap<>();
            map.put("text", infoList[i]);
            listData.add(map);
        }

        simpleAdapter = new SimpleAdapter(this, listData, R.layout.video_list_item,
                new String[]{"text"}, new int[]{R.id.item_text});
        listView.setAdapter(simpleAdapter);

        waitAnimation = AnimationUtils.loadAnimation(this, R.anim.watting_anim);
        waitAnimation.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListeners();
    }

    private void initListeners() {
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMenuOpened) {
                    topBar.setVisibility(View.VISIBLE);
                    bottomBar.setVisibility(View.VISIBLE);
                } else {
                    topBar.setVisibility(View.GONE);
                    bottomBar.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                }
                isMenuOpened = !isMenuOpened;
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btPlayState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    btPlayState.setBackgroundResource(R.drawable.ic_pause);
                } else {
                    btPlayState.setBackgroundResource(R.drawable.ic_play);
                }
                isPlaying = !isPlaying;
            }
        });
        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }

        });

        btVideoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getVisibility() == View.GONE) {
                    listView.setVisibility(View.VISIBLE);
                } else if(listView.getVisibility()==View.VISIBLE){
                    listView.setVisibility(View.GONE);
                }
            }
        });

        ivAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoading) {
                    ivAnim.startAnimation(waitAnimation);
                } else {
                    ivAnim.clearAnimation();
                }
                isLoading = !isLoading;
            }
        });

    }
}
