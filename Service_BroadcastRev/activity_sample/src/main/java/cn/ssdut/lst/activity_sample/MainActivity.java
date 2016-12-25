package cn.ssdut.lst.activity_sample;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
        Log.d("tag","---调用MainActivity----onCreate()---");
    }
    private void initButton(){
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, TestActivity.class);
                t.putExtra("info", "来自MainActivity的问候");
                startActivity(t);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent t = new Intent();
                t.setClass(MainActivity.this,TestActivity2.class);
                startActivityForResult(t,0);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, StandardActivity.class);
                startActivity(t);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, SgTopActivity.class);
                startActivity(t);
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, SgTaskActivity.class);
                startActivity(t);
            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, SgInstanceActivity.class);
                startActivity(t);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 & resultCode==1){
            String str = data.getStringExtra("note");
            Toast.makeText(MainActivity.this,"note:"+str,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onStart() {
        Log.d("tag","---调用MainActivity----onStart()---");
        super.onStart();
    }
    @Override
    protected void onRestart() {
        Log.d("tag","---调用MainActivity----onRestart()---");
        super.onRestart();
    }
    @Override
    protected void onStop() {
        Log.d("tag","---调用MainActivity----onStop()---");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d("tag","---调用MainActivity----onDestroy()---");
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        Log.d("tag","---调用MainActivity----onPause()---");
        super.onPause();
    }
    @Override
    protected void onResume() {
        Log.d("tag","---调用MainActivity----onResume()---");
        super.onResume();
    }
}
