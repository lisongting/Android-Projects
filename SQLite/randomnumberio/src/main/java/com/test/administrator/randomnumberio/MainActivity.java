package com.test.administrator.randomnumberio;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean hasSD ;
        hasSD = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        File f= Environment.getExternalStorageDirectory();
        String state = Environment.getExternalStorageState();
        try{
            if(hasSD){
                Toast.makeText(MainActivity.this, "有sd卡.\n canonicalPath"+f.getCanonicalPath()+
                        "\n绝对路径:"+f.getAbsolutePath()+"\nstorageState:"+state, Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(MainActivity.this, "没有sd", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
