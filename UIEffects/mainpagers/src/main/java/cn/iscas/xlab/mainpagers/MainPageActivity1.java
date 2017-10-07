package cn.iscas.xlab.mainpagers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by lisongting on 2017/10/6.
 */

public class MainPageActivity1 extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ImageButton planeButton;
    private ImageButton trainButton;
    private ImageButton userButton;
    private TextView planeText;
    private TextView trainText;
    private TextView userText;

    private String[] list = new String[]{"我的航班","我的车次","个人中心"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mainpage1);

        initView();
        initEvents();

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return SimpleFragment.getInstance(list[position]);
            }

            @Override
            public int getCount() {
                return list.length;
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
    }

    private void initEvents() {
        planeButton.setOnClickListener(this);
        trainButton.setOnClickListener(this);
        userButton.setOnClickListener(this);
        planeText.setOnClickListener(this);
        trainText.setOnClickListener(this);
        userText.setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                reset();
                switch (position) {
                    case 0:
                        planeButton.setImageResource(R.drawable.plane_selected);
                        planeText.setTextColor(Color.parseColor("#33B5E5"));
                        break;
                    case 1:
                        trainButton.setImageResource(R.drawable.train_selected);
                        trainText.setTextColor(Color.parseColor("#33B5E5"));
                        break;
                    case 2:
                        userButton.setImageResource(R.drawable.user_selected);
                        userText.setTextColor(Color.parseColor("#33B5E5"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        planeButton = (ImageButton) findViewById(R.id.bt_plane);
        trainButton = (ImageButton) findViewById(R.id.bt_train);
        userButton = (ImageButton) findViewById(R.id.bt_user);
        planeText = (TextView) findViewById(R.id.text_plane);
        trainText = (TextView) findViewById(R.id.text_train);
        userText = (TextView) findViewById(R.id.text_user);

        planeButton.setImageResource(R.drawable.plane_selected);
        planeText.setTextColor(Color.parseColor("#33B5E5"));
    }

    public void reset(){
        planeButton.setImageResource(R.drawable.plane);
        trainButton.setImageResource(R.drawable.train);
        userButton.setImageResource(R.drawable.user);
        planeText.setTextColor(Color.parseColor("#a3a3a3"));
        userText.setTextColor(Color.parseColor("#a3a3a3"));
        trainText.setTextColor(Color.parseColor("#a3a3a3"));

    }


    @Override
    public void onClick(View v) {
        reset();
        switch (v.getId()) {
            case R.id.bt_plane:
                viewPager.setCurrentItem(0);
                planeButton.setImageResource(R.drawable.plane_selected);
                planeText.setTextColor(Color.parseColor("#33B5E5"));
                break;
            case R.id.bt_train:
                viewPager.setCurrentItem(1);
                trainButton.setImageResource(R.drawable.train_selected);
                trainText.setTextColor(Color.parseColor("#33B5E5"));
                break;
            case R.id.bt_user:
                viewPager.setCurrentItem(2);
                userButton.setImageResource(R.drawable.user_selected);
                userText.setTextColor(Color.parseColor("#33B5E5"));
        }
    }
}
