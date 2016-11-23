package cn.ssdut.lst.servicetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/20.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onStart() {
        Log.d("tag","SecondActivity --onStart()");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag","SecondActivity --onCreate()");
        setContentView(R.layout.second_layout);
    }

    @Override
    protected void onStop() {
        Log.d("tag","SecondActivity --onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("tag","SecondActivity --onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d("tag","SecondActivity --onReStart()");
        super.onRestart();
    }

    protected void onResume() {
        Log.d("tag","SecondActivity --onResume()");
        super.onResume();
    }

    protected void onPause() {
        Log.d("tag","SecondActivity --onPause()");
        super.onPause();
    }
}
