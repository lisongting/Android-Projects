package com.lst.botdialog;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                selectFile();
                break;
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

    private void selectFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, "请选择一个音频文件"), 1);

    }

    private String getRealPathFromUri(Uri uri) {
        String res ;
        String[] proj = {MediaStore.Audio.AudioColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor == null) {
            res = "";
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
            res = cursor.getString(index);
            cursor.close();
        }

        return res;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
//                Toast.makeText(this, "文件URI："+uri.getPath().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "文件路径: "+getRealPathFromUri(uri), Toast.LENGTH_SHORT).show();
                Log.i("tag", "文件URI：" + uri.getPath().toString());
                Log.i("tag", "文件绝对路径：" + getRealPathFromUri(uri));
            } else {
                Toast.makeText(this, "请选择文件", Toast.LENGTH_SHORT).show();
            } 
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
