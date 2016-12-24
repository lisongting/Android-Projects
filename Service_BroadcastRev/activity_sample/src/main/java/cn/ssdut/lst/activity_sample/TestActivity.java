package cn.ssdut.lst.activity_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/24.
 */
public class TestActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act1_layout);
        Intent recvIntent = getIntent();
        Toast.makeText(TestActivity.this, "info:"+recvIntent.getStringExtra("info"), Toast.LENGTH_SHORT).show();
    }


}
