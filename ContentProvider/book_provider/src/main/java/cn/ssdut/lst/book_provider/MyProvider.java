package cn.ssdut.lst.book_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/28.
 */

public class MyProvider extends ContentProvider {
    private static  final String AUTHORITY = "cn.ssdut.lst.MyProvider";
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static MyDatabaseHelper dbHelper ;
    private ContentObserver observer;
    static {
        //在UriMatcher中注册Uri
        matcher.addURI(AUTHORITY,"books",66);
    }
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(),"MyData",1);//MyData是数据库名
        observer = new MyObserver(getContext(), new Handler());
        getContext().getContentResolver().registerContentObserver(
                Uri.parse("content://cn.ssdut.lst.MyProvider"),true,observer);
        return true;

    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (matcher.match(uri) == 66) {
            long rowId = db.insert("bookShelf","_id",values);
            if (rowId > 0) {
                Uri newUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(newUri,observer);
                Toast.makeText(getContext(),"信息插入成功,ID:"+rowId,Toast.LENGTH_SHORT).show();
                return newUri;
            }
        }else{
            return null;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int num=0;
        if (matcher.match(uri) == 66) {
            num = db.delete("bookShelf",selection,selectionArgs);
        }else {
            return num;
        }
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs, String sort) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (matcher.match(uri) == 66) {

        }else {
            return null;
        }
        return null;
    }
    public String getType(Uri uri) {
        if(matcher.match(uri)==66){
            return "vnd.android.cursor.item/cn.ssdut.lst";
        }else{
            return null;
        }
    }



}
