package com.test.administrator.layerdrabletest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/10/21.
 */
public class AnimActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_layout);
        final ImageView image = (ImageView)findViewById(R.id.image);
        //加载动画资源
        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.my_anim);
        anim.setFillAfter(true);//动画结束后保留图片的变换结果
        Button bn = (Button)findViewById(R.id.bn);
        bn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                image.startAnimation(anim);
            }
        });

    }
}
