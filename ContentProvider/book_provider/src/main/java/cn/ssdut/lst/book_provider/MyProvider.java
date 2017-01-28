package cn.ssdut.lst.book_provider;

import android.content.ContentProvider;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Administrator on 2017/1/28.
 */

public class MyProvider extends ContentProvider {
    private static  final String AUTHORITY = "cn.ssdut.lst.MyProvider";
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MyDatabaseHelper dbHelper ;
    static {
        //在UriMatcher中注册Uri
        matcher.addURI(AUTHORITY,"books",66);
    }
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(),"MyData",1);
        return true;
    }

    public Cursor query(Uri uri,String[] projection,String where,String[] whereArgs,String sort) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (matcher.match(uri) == 66) {

        }
    }
    public String getType(Uri uri) {
        if(matcher.match(uri)==66){
            return "vnd.android.cursor.item/cn.ssdut.lst";
        }
    }



}
