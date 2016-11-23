package com.test.administrator.uitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.QuickContactBadge;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QuickContactBadge quck = (QuickContactBadge)findViewById(R.id.quickContact);
        quck.assignContactFromEmail("501648152@qq.com",false);
    }
}
