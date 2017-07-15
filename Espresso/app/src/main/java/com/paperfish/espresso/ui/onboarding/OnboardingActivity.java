package com.paperfish.espresso.ui.onboarding;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.paperfish.espresso.MainActivity;
import com.paperfish.espresso.R;
import com.paperfish.espresso.util.SettingsUtil;

/**
 * Created by lisongting on 2017/7/8.
 */

public class OnboardingActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private AppCompatButton buttonFinish;
    private ImageButton buttonPre;
    private ImageButton buttonNext;
    private ImageView[] indicators;
    private int[] bgColors;
    private int currentPosition;
    private static final int MSG_DATA_INSERT_FINISH = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DATA_INSERT_FINISH:
                    buttonFinish.setText(R.string.onboarding_finish_button_description);
                    buttonFinish.setEnabled(true);
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //如果APP第一次启动，则启动引导页面
        if (sp.getBoolean(SettingsUtil.KEY_FIRST_LAUNCH, true)) {
            setContentView(R.layout.activity_onboarding);

//            new InitCompaniesDataTask().execute();

            initViews();

            initData();
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                //position是界面的索引下标，
                // 手指右滑时：positionOffset变化:0.0-1.0   positionOffsetPixels:0-1079
                //手指左滑时：positionOffset变化：1.0-0.0   positionOffsetPixels:1079-0
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.i("tag", "viewPager -- onPageScrolled()");
                    Log.i("tag", "position:" + position + ",positionOffset:" + positionOffset +
                            ",positionOffsetPixel:" + positionOffsetPixels);
                    // 第二个参数是startColor，第三个参数是endColor。第一个参数是渐变程度，指从start颜色到end颜色渐变程度
                    int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset, bgColors[position],
                            bgColors[position == 2 ? position : position + 1]);
                    viewPager.setBackgroundColor(colorUpdate);

                }

                @Override
                public void onPageSelected(int position) {
                    Log.i("tag", "viewPager -- onPageSelected(),position：" + position);
                    currentPosition = position;
                    updateIndicators(position);
                    viewPager.setBackgroundColor(bgColors[position]);
                    buttonPre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                    buttonFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                    buttonNext.setVisibility(position==2?View.GONE:View.VISIBLE);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.i("tag", "viewPager -- onPageScrollStateChanged()");
                }
            });

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition += 1;
                    viewPager.setCurrentItem(currentPosition, true);
                }
            });

            buttonFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(SettingsUtil.KEY_FIRST_LAUNCH, false);
                    editor.apply();
                    navigateToMainActivity();
                    enableShortCuts();
                }
            });

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition -= 1;
                    viewPager.setCurrentItem(currentPosition, true);
                }
            });
        }else{
            navigateToMainActivity();
            finish();
        }


    }

    private void enableShortCuts() {

    }

    private void navigateToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void updateIndicators(int position) {
        for(int i=0;i<indicators.length;i++) {
            //如果选中，就更改颜色
            indicators[i].setBackgroundResource(i == position ? R.drawable.onboarding_indicator_selected
                    : R.drawable.onboarding_indicator_unselected);
        }
    }

    private void initData() {
        bgColors = new int[]{ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.cyan_500),
                ContextCompat.getColor(this, R.color.light_blue_500)};
    }

    private void initViews() {
        OnboardingPagerAdapter pagerAdapter = new OnboardingPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        buttonFinish = (AppCompatButton) findViewById(R.id.buttonFinish);
//        buttonFinish.setText(R.string.onboarding_finish_button_description_wait);
//        buttonFinish.setEnabled(false);
        buttonFinish.setText("start");
        buttonNext = (ImageButton) findViewById(R.id.imageButtonNext);
        buttonPre = (ImageButton) findViewById(R.id.imageButtonPre);
        indicators = new ImageView[] {
                (ImageView) findViewById(R.id.imageViewIndicator0),
                (ImageView) findViewById(R.id.imageViewIndicator1),
                (ImageView) findViewById(R.id.imageViewIndicator2)};
    }


    private class InitCompaniesDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
//            CompaniesRepository.getInstance(CompaniesLocalDataSource.getInstance()).initData();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            handler.sendEmptyMessage(MSG_DATA_INSERT_FINISH);
        }

    }
}
