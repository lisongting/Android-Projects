package com.paperfish.streammediatest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lisongting on 2017/10/26.
 */

public class CustomVideoFragment extends Fragment {

    private SurfaceView surfaceView;
    private RelativeLayout topBar,bottomBar;
    private ImageButton btBack;
    private ImageButton btPlayState;
    private ImageButton btFullScreen;
    private ImageButton btVideoList;
    private ImageView ivAnim;

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private String[] infoList = {"RGB图像","深度图像"};
    private List<Map<String,String>> listData;
    private boolean isMenuOpened ;
    private boolean isPlaying;
    private boolean isLoading;
    private Animation waitAnimation;

    public CustomVideoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("onCreateView()");
        View v = inflater.inflate(R.layout.layout_custom_video, container, false);
        surfaceView = (SurfaceView) v.findViewById(R.id.sv_video);
        topBar = (RelativeLayout) v.findViewById(R.id.top_bar);
        bottomBar = (RelativeLayout) v.findViewById(R.id.bottom_bar);
        btBack = (ImageButton) v.findViewById(R.id.ib_back);
        btPlayState = (ImageButton) v.findViewById(R.id.ib_play_state);
        btFullScreen = (ImageButton) v.findViewById(R.id.ib_screen_state);
        btVideoList = (ImageButton) v.findViewById(R.id.ib_list);
        ivAnim = (ImageView) v.findViewById(R.id.iv_anim);
        listView = (ListView) v.findViewById(R.id.list_view);

        isMenuOpened = true;
        initView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume()");
        initListeners();
    }

    private void initView() {
        listData = new ArrayList<>();
        for(int i=0;i<infoList.length;i++) {
            Map<String, String> map = new HashMap<>();
            map.put("text", infoList[i]);
            listData.add(map);
        }

        simpleAdapter = new SimpleAdapter(getContext(), listData, R.layout.video_list_item,
                new String[]{"text"}, new int[]{R.id.item_text});
        listView.setAdapter(simpleAdapter);

        waitAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.watting_anim);
        waitAnimation.setInterpolator(new LinearInterpolator());

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
                Toast.makeText(getActivity(), "Click SurfaceView", Toast.LENGTH_SHORT).show();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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

                startActivity(new Intent(getContext(),FullScreenVideoActivity.class));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart()");
    }



    @Override
    public void onPause() {
        super.onPause();
        log("onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log("onSaveInstanceState()");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("onDetach()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy()");
    }

    private void log(String s) {
        Log.i("tag", "Fragment -- " + s);
    }
}
