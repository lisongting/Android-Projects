package com.test.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button)findViewById(R.id.Start_Nomal_Activity);
        Button b2 = (Button)findViewById(R.id.Start_Dialog_Activity);
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent);
            }
        });

    }

    protected void onStart(){
        super.onStart();
        Log.d("MainActivity","onStart");
    }
    protected void onResume(){
        super.onResume();
        Log.d("MainActivity","onResume");
    }
    protected void onPause(){
        super.onPause();
        Log.d("MainActivity","onPause");
    }

}
