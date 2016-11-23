package com.test.administrator.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Administrator on 2016/10/22.
 */
public class FirstProvider extends ContentProvider {
    public boolean onCreate(){
        System.out.println("===onCreate方法被调用===");
        return true;
    }

    public String getType(Uri uri){
        return null;
    }
    @Override
    public Cursor query(Uri uri,String[] projection,String where,String[] whereargs,String sortOrder){
        System.out.println(uri+"===query方法被调用===");
        System.out.println("where参数为"+where);
        return null;
    }
    @Override
    public Uri insert(Uri uri,ContentValues values){
        System.out.println(uri+"===insert方法被调用===");
        System.out.println("values参数为"+values);
        return null;
    }
    public int update(Uri uri,ContentValues values,String where,String[] whereArgs){
        System.out.println(uri+"===update方法被调用===");
        System.out.println("where参数为"+where+",values参数为"+values);
        return 0;
    }
    public int delete(Uri uri,String where,String[] whereArgs){
        System.out.println(uri+"===delete方法被调用===");
        System.out.println("where参数为"+where);
        return 0;
    }
}
