package cn.ssdut.lst.activity_sample;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
public class TestActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act1_layout);
        Intent recvIntent = getIntent();
        Toast.makeText(TestActivity.this, "info:"+
                recvIntent.getStringExtra("info"), Toast.LENGTH_SHORT).show();
        Log.d("tag","---调用TestActivity----onCreate()---");
    }
    @Override
    protected void onStart() {
        Log.d("tag","---调用TestActivity----onStart()---");
        super.onStart();
    }
    @Override
    protected void onRestart() {
        Log.d("tag","---调用TestActivity----onRestart()---");
        super.onRestart();
    }
    @Override
    protected void onStop() {
        Log.d("tag","---调用TestActivity----onStop()---");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d("tag","---调用TestActivity----onDestroy()---");
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        Log.d("tag","---调用TestActivity----onPause()---");
        super.onPause();
    }
    @Override
    protected void onResume() {
        Log.d("tag","---调用TestActivity----onResume()---");
        super.onResume();
    }
}
