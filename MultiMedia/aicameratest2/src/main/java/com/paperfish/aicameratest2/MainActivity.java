package com.paperfish.aicameratest2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 在app中集成caffe2的物体识别功能
 *
 * 2017.8.16
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag";

    private AICameraFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log("onCreate");
        fragment = AICameraFragment.getInstance();
//        getSupportFragmentManager().beginTransaction().addToBackStack()
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
        int rc = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();

        } else {
            //请求照相机权限
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, 11);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");

    }


    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("权限被拒绝");
            builder.setMessage("前去设置权限");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("取消啊", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private void log(String string) {
        Log.i(TAG, "MainActivity -- "+string);
    }
}
