package com.test.administrator.configurationtest;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bn =(Button)findViewById(R.id.button);

        bn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Configuration cfg = getResources().getConfiguration();
                if(cfg.orientation==Configuration.ORIENTATION_LANDSCAPE){
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                if(cfg.orientation==Configuration.ORIENTATION_PORTRAIT){
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
    }

    public void onConfigurationChanged(Configuration newcfg){
        super.onConfigurationChanged(newcfg);
        String str = newcfg.orientation==Configuration.ORIENTATION_LANDSCAPE? "横向屏幕":"竖向屏幕";
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
    }
}
