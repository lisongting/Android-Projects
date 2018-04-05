package com.lst.botdialog;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btShow,btHide,btTest;
    private RelativeLayout bottom;
    private ValueAnimator translateAnimIn,translateAnimOut;
    private int popHeight;
    private RelativeLayout parentView;
    private ImageView expandImg,prevExpandImg;
    private LinearLayout prevBottomLine,bottomLine;

    private boolean isShowingList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btShow = findViewById(R.id.bt1);
        btHide = findViewById(R.id.bt2);
        btTest = findViewById(R.id.bt3);
        btShow.setOnClickListener(this);
        btHide.setOnClickListener(this);
        btTest.setOnClickListener(this);


        parentView = findViewById(R.id.parent_view);
        bottom = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_bottom_popup,null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottom.setLayoutParams(params);
        parentView.addView(bottom);
        bottom.setVisibility(View.INVISIBLE);

        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "cli", Toast.LENGTH_SHORT).show();
            }
        });
        prevBottomLine = findViewById(R.id.ll_prev_bottom_line);
        bottomLine = findViewById(R.id.ll_bottom_line);
//        prevExpandImg = findViewById(R.id.prev_expand_icon);
        expandImg = bottom.findViewById(R.id.expand_icon);

        prevBottomLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowingList) {
                    showList();
                }
            }
        });

        bottomLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowingList) {
                    hideList();
                } else {
                    showList();
                }
            }
        });

    }

    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                showList();
                break;
            case R.id.bt2:
                hideList();
                break;
            case R.id.bt3:
                Toast.makeText(this, "TEST", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.drawer_title:
//                if (bottom.getVisibility() == View.VISIBLE) {
//                    Toast.makeText(this, "popViewHeight:"+popViewHeight, Toast.LENGTH_LONG).show();
//                    translateAnimOut = new TranslateAnimation(0, 0, 0, popViewHeight);
//                    translateAnimOut.setDuration(500);
//                    translateAnimOut.setInterpolator(new DecelerateInterpolator());
//                    translateAnimOut.setFillAfter(true);
//                    bottom.startAnimation(translateAnimOut);
//                    bottom.setVisibility(View.INVISIBLE);
//                }
//                break;
            default:
                break;
        }
    }

    private void initAnimation() {
        if (translateAnimIn != null && translateAnimOut != null) {
            return;
        }
        popHeight = bottom.getHeight()-expandImg.getHeight();
        Toast.makeText(this, "popViewHeight:"+popHeight, Toast.LENGTH_LONG).show();
        translateAnimIn = ValueAnimator.ofInt(popHeight, 0);
        translateAnimIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                bottom.setTranslationY(value);
            }
        });
        translateAnimIn.setDuration(500);
        translateAnimIn.setInterpolator(new DecelerateInterpolator());


        translateAnimOut = ValueAnimator.ofInt(0, popHeight);
        translateAnimOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                bottom.setTranslationY(value);
            }
        });
        translateAnimOut.setDuration(500);
        translateAnimOut.setInterpolator(new DecelerateInterpolator());
    }

    private void showList(){
        if (!isShowingList) {
            initAnimation();
            bottom.setVisibility(View.VISIBLE);
            translateAnimIn.start();
            expandImg.setImageResource(R.drawable.arrow_down);
            Drawable d = getResources().getDrawable(R.drawable.arrow_down,null);
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
//            expandImg.setCompoundDrawables(null,null,d,null);

//            expandImg
            prevBottomLine.setVisibility(View.INVISIBLE);
            isShowingList = true;
        }

    }

    private void hideList(){
        if (isShowingList) {
            initAnimation();
            translateAnimOut.start();
            expandImg.setImageResource(R.drawable.arrow_up);
            Drawable d = getResources().getDrawable(R.drawable.arrow_up,null);
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
//            expandImg.setCompoundDrawables(null,null,d,null);
            isShowingList = false;
        }

    }

}
