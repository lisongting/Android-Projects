package cn.ssdut.lst.ledlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * [错误]
 * 按照李刚那本书上的敲的，
 * 那本书的这个源码是删除了MainActivity的。
 * 如果我删除了MainActivity，则安装不上，安装时报错，Default Activity not found。
 * 如果不删除MainActivity，则安装app之后，在桌面控件处根本找不到这个液晶时钟控件！
 * (这本书真的是坑的一批)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
