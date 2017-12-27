package cn.lst.facerecog.recognize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import cn.lst.facerecog.R;
import cn.lst.facerecog.Util;


/**
 * Created by lisongting on 2017/12/14.
 */

public class RecogActivity extends AppCompatActivity {

    private RecogFragment recogFragment;
    private ImageButton btBack;
    private RecogPresenter presenter;
    private ImageButton btSwitch;
    private FrameLayout frameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recognize);

        frameLayout = findViewById(R.id.container);
        btBack = findViewById(R.id.ib_back);
        btSwitch = findViewById(R.id.bt_switch);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (metrics.heightPixels > 2000) {
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(width * 0.6), (int)(height * 0.6));
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            frameLayout.setLayoutParams(params);
            frameLayout.requestLayout();
        }

        if (savedInstanceState == null) {
            recogFragment = new RecogFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, recogFragment, "recogFragment")
                    .commit();
        } else {
            recogFragment = (RecogFragment) getSupportFragmentManager().findFragmentByTag("recogFragment");
        }
        presenter = new RecogPresenter(recogFragment);


        initView();
        initOnClickListeners();
    }

    private void initView() {
        //获取状态栏高度，显示一个占位的View(该view和actionbar颜色相同)，达到沉浸式状态栏效果
        View status_bar = findViewById(R.id.status_bar_view);
        ViewGroup.LayoutParams params = status_bar.getLayoutParams();
        params.height = Util.getStatusBarHeight(this);
        status_bar.setLayoutParams(params);
    }

    private void initOnClickListeners() {
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogFragment.switchCamera();
            }
        });
    }

}
