package com.example.cemaratest;

import android.content.Intent;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String regex = "[\\u4E00-\\u9FA5]{2,5}(?:·[\\u4E00-\\u9FA5]{2,5})*";
    private TextInputLayout textInputLayout;
    private EditText editText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInputLayout = (TextInputLayout) findViewById(R.id.id_text_input_layout);
        editText = (EditText) findViewById(R.id.id_edit_text);
//        Camera2 camera2 ;
        String s = "李小明";
        s.matches("[\\u4E00-\\u9FA5]{2,5}(?:·[\\u4E00-\\u9FA5]{2,5})*");

        CameraDevice d;
        CameraManager manager;
    }

    public void startCapture(View view) {
        startActivity(new Intent(this, CameraActivity.class));
    }
}
