package com.test.administrator.layerdrabletest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/10/25.
 */
public class StyleActivity extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(R.style.CrazyTheme);
        setContentView(R.layout.style_layout);
    }

}
