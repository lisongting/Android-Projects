package com.test.administrator.firstresolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ContentResolver resolver;
    Uri uri = Uri.parse("content://com.ssdut.provider.firstprovider/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
    }
    public void query(View source){
        Cursor c = resolver.query(uri,null,"query_where",null,null);
        Toast.makeText(MainActivity.this, "ContentProvider返回的Cursor为"+c, Toast.LENGTH_SHORT).show();
    }

    public void insert(View source){
        ContentValues values = new ContentValues();
        values.put("name","疯狂android");
        //实际调用的是Uri对应的ContentProvider对应的insert方法
        Uri newUri = resolver.insert(uri,values);
        Toast.makeText(MainActivity.this, "ContentProvider新插入记录的uri为:"+newUri, Toast.LENGTH_SHORT).show();
    }
    public void update(View v){
        ContentValues values = new ContentValues();
        values.put("name","疯狂java");
        int count = resolver.update(uri,values,"update_where",null);
        Toast.makeText(MainActivity.this, "ContentProvider更新的记录数为"+count, Toast.LENGTH_SHORT).show();
    }
    public void delete (View v){
        int count = resolver.delete(uri,"delete_where",null);
        Toast.makeText(MainActivity.this, "ContentProvider删除的记录数为:"+count, Toast.LENGTH_SHORT).show();
    }
}
