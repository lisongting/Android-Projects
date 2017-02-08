package cn.ssdut.lst.foregroundservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, ForeGroundService.class);
    }
    public void startService(View view) {
        startService(intent);
    }
    public void stopService(View view) {
        stopService(intent);
    }
}
