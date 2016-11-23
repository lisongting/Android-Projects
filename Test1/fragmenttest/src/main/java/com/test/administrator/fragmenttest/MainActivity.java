package com.test.administrator.fragmenttest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    /*
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                AnotherRightFragment fragment = new AnotherRightFragment();
                FragmentManager fgManager = getFragmentManager();
                FragmentTransaction transaction = fgManager.beginTransaction();
                transaction.replace(R.id.right_layout,fragment);//用fragment替换原来的R.id.right_layout
                transaction.addToBackStack(null);//给事务添加返回栈，单击返回按钮回到上一个界面
                transaction.commit();//提交事务

                break;
            default:
                break;
        }
    }*/
}
