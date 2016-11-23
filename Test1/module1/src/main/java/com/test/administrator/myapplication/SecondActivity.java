package com.test.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);为什么这个代码不起任何作用

        //Intent intent = getIntent();
        //String str2 = intent.getStringExtra("extra_data");
        //Toast.makeText(SecondActivity.this,str2,Toast.LENGTH_LONG).show();
        setContentView(R.layout.second_layout);
        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return","Displayed FirstActiity ");
                setResult(RESULT_OK,intent);
                System.out.println("result_ok的值是"+RESULT_OK);
                finish();
            }
        });
    }
    public  void onBackPressed(){
        Toast.makeText(SecondActivity.this,"退出活动二",Toast.LENGTH_SHORT).show();
    }
}
