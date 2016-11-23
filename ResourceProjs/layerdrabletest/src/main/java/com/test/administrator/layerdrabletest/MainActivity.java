package com.test.administrator.layerdrabletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void ClipDrawable(View v){
        Intent intent = new Intent(MainActivity.this,ClipDrawActivity.class);
        startActivity(intent);
    }
    public  void Animation(View v){
        Intent intent = new Intent(MainActivity.this,AnimActivity.class);
        startActivity(intent);
    }
    public void xmlParse(View v){
        Intent intent = new Intent(MainActivity.this,XmlParseActivity.class);
        startActivity(intent);
    }
    public void styleShow(View v){
        Intent intent = new Intent(MainActivity.this,StyleActivity.class);
        startActivity(intent);
    }
    public void rawResCall(View v){
        Intent intent = new Intent(MainActivity.this,RawResActivity.class);
        startActivity(intent);
    }
}
