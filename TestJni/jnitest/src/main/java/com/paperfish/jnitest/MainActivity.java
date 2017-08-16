package com.paperfish.jnitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * JNI的使用
 *
 * 总结一下流程：
 * 编写静态方法（用java声明）-->编译生成class文件--->编译生成h文件---->编写C文件（用C/C++实现）
 * ---->配置NDK---->配置so库---->在Activity调用（Java调用C/C++）
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.textView);

        //调用jni方法
        tv.setText(myJNI.sayHello());

    }
}
