package com.test.administrator.uitest;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.QuickContactBadge;
@TargetApi(21)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QuickContactBadge quck = (QuickContactBadge)findViewById(R.id.quickContact);
        quck.assignContactFromEmail("501648152@qq.com",false);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Display display = getWindowManager().getDefaultDisplay();
        Log.i("TAG", "rotation:" + display.getRotation());
        Log.i("TAG", "name:" + display.getName());
        super.onSaveInstanceState(outState);
    }
}
