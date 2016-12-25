package cn.ssdut.lst.activity_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by Administrator on 2016/12/24.
 */
public class TestActivity2 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act2_layout);
    }

    public  void onBackPressed() {
        Intent tmp = new Intent();
        tmp.putExtra("note","来自TestActivity2的温馨问候");
        setResult(1, tmp);
        finish();
    }
}
