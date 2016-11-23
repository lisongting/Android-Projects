package com.test.administrator.test2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class CustomViewActivity extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //获取布局文件中的线性布局管理器Linealayout
        LinearLayout root = (LinearLayout)findViewById(R.id.root);
        //创建DrawView组件
        final DrawView draw = new DrawView(this);
        draw.setMinimumHeight(500);
        draw.setMinimumWidth(300);
        root.addView(draw);

    }
}