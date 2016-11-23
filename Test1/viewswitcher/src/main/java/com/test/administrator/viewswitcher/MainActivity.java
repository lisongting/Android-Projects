package com.test.administrator.viewswitcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int NUMBER_PER_SCREEEN= 9;
    private ArrayList<ImageView> items = new ArrayList<>();
    private int screenNo = -1;//记录当前正在显示第几屏
    private int screenCount;//保存程序所占的总屏数
    ViewSwitcher viewSwitcher;
    LayoutInflater layoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
