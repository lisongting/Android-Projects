package com.test.administrator.dynamicfragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private Button button2;
    private ContentFragment contentFragment;
    private FriendFragment fiendFragment;
    private BaseFragment baseFragment;
    private android.support.v4.app.Fragment f ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        button1 = (Button)findViewById(R.id.bt1);
        button2 = (Button)findViewById(R.id.bt2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        setDefaultFragment();//设置为默认的Fragment
    }

    private void setDefaultFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        baseFragment = new BaseFragment();

        //动态管理Fragment之常见操作
        //1.添加
        transaction.add(R.id.id_content, baseFragment);
        //2.替换
        transaction.replace(R.id.id_content, baseFragment);
        //3.移除
        transaction.remove(baseFragment);
        //4.隐藏
        transaction.hide(baseFragment);
        //5.显示
        transaction.show(baseFragment);
        transaction.commit();
    }
    public void onClick(View v){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch(v.getId()){
            case R.id.bt1:
                if (contentFragment == null) {
                    contentFragment = new ContentFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.id_content, contentFragment);
                break;
            case R.id.bt2:
                if (fiendFragment == null) {
                    fiendFragment = new FriendFragment();
                }
                transaction.replace(R.id.id_content, fiendFragment);
                break;
        }
        transaction.addToBackStack("cc");
        // 事务提交
        transaction.commit();

    }

}
