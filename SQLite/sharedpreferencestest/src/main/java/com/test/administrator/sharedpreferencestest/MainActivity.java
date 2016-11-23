package com.test.administrator.sharedpreferencestest;

import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("crazyIt",MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void fun1(View source){
        String time = preferences.getString("time",null);
        int randNum = preferences.getInt("random",0);
        String result = time ==null?"还未写入数据":"写入时间为："+time+"\n上次生成的随机数为："+randNum;
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
    }
    public void fun2(View source){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日" +
                "hh:mm:ss");
        //存入当前时间
        editor.putString("time",sdf.format(new Date()));
        //存入一个随机数
        editor.putInt("random",(int)(Math.random()*100));
        editor.commit();
    }
}
