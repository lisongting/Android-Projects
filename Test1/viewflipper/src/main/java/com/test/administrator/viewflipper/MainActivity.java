package com.test.administrator.viewflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlipper = (ViewFlipper)findViewById(R.id.details);

    }
    public void prev(View source){
        viewFlipper.setInAnimation(this,@android:);
        viewFlipper.setOutAnimation(this,R.anim.slide_out_left);
        viewFlipper.showPrevious();
        viewFlipper.stopFlipping();//停止自动播放
    }
    public void auto(View source){
        viewFlipper.setInAnimation(this,R.anim.slide_out);
        viewFlipper.setOutAnimation(this,R.anim.slide_out_left);
        viewFlipper.showPrevious();
        viewFlipper.stopFlipping();
    }

}
