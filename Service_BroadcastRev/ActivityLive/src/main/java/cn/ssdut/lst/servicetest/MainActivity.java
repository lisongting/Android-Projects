package cn.ssdut.lst.servicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        Log.d("tag","MainActivity --onStart()");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag","MainActivity --onCreate()");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        Log.d("tag","MainActivity --onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("tag","MainActivity --onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d("tag","MainActivity --onReStart()");
        super.onRestart();
    }

    protected void onResume() {
        Log.d("tag","MainActivity --onResume()");
        super.onResume();
    }

    protected void onPause() {
        Log.d("tag","MainActivity --onPause()");
        super.onPause();
    }

    public void second(View source){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
