package com.paperfish.hotfixtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 测试阿里的Sophix热修复服务
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "热修复Toast：Sophix有效", Toast.LENGTH_LONG).show();
    }
}
