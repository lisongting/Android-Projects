package com.test.administrator.dictprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DictProvider extends ContentProvider {
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS=1;
    private static final int WORD=2;
    private MyDatabaseHelper dbOpenhelper;
    static {
        //为UriMatcher 注册两个Uri
        matcher.addURI(Words.AUTHORITY,"words",WORDS);
        matcher.addURI(Words.AUTHORITY,"word#",WORD);
    }
    public boolean onCreate(){
        dbOpenhelper = new MyDatabaseHelper(this.getContext(),"myDict3",1);
        return true;
    }
    public String getType(Uri uri){
        switch(matcher.match(uri)){
            case WORDS:
                return "vnd.android.cursor.dir/com.ssdut.lst";
            case WORD:
                return "vnd.android.cursor.item/com.ssdut.lst";
            default:
                System.out.println("未知的Uri"+uri);
                throw new IllegalArgumentException("未知的Uri"+uri);
        }
    }
    //通过contentProvider的query 对底层数据库进行查询
    public Cursor query(Uri uri,String[] projection,String where,String[] whereArgs,String sortOrder ){
        SQLiteDatabase db = dbOpenhelper.getReadableDatabase();
        switch(matcher.match(uri)){
            case WORDS:
                return db.query("dict",projection,where,whereArgs,null,null,sortOrder);
            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID +"="+id;
                if(where !=null&&!"".equals(where))
                    whereClause = whereClause+"and"+where;
                return db.query("dict",projection,whereClause,whereArgs,null,null,sortOrder);
            default:
                System.out.println("未知的Uri"+uri);
                throw new IllegalArgumentException("未知的Uri"+uri);
        }
    }
    public Uri insert(Uri uri,ContentValues values){
        SQLiteDatabase db = dbOpenhelper.getReadableDatabase();
        switch(matcher.match(uri)){
            case WORDS:
                long rowId = db.insert("dict",Words.Word._ID,values);
                if(rowId>0){
                    Uri wordUri = ContentUris.withAppendedId(uri,rowId);
                    getContext().getContentResolver().notifyChange(wordUri,null);
                    Toast.makeText(getContext(),"数据插入成功",Toast.LENGTH_SHORT).show();
                    return wordUri;
                }
                break;
            default:
                System.out.println("未知的Uri"+uri);
                throw new IllegalArgumentException("未知的Uri"+uri);
        }
        return null;
    }
    public int update(Uri uri,ContentValues values,String where,String[] whereArgs){
        SQLiteDatabase db = dbOpenhelper.getReadableDatabase();
        int num;
        switch(matcher.match(uri)){
            case WORDS:
                num = db.update("dict",values,where,whereArgs);
                break;
            case WORD:
                long id = ContentUris.parseId(uri);//取出想要修改的记录的id
                String whereClause = Words.Word._ID+"="+id;
                if(whereClause!=null&&!where.equals("")){
                    whereClause = whereClause+"and"+where;
                }
                num = db.update("dict",values,whereClause,whereArgs);
                break;
            default:
                throw new IllegalArgumentException("未知的Uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        if(num>0){
            Toast.makeText(getContext(), "数据修改成功", Toast.LENGTH_SHORT).show();
        }
        return num;
    }

    public int delete(Uri uri,String where,String[]whereArgs){
        SQLiteDatabase db = dbOpenhelper.getReadableDatabase();
        int num=0;
        switch(matcher.match(uri)){
            case WORDS:
                num = db.delete("dict",where,whereArgs);
                break;
            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID+"="+id;
                if(whereClause!=null&&!where.equals("")){
                    whereClause = whereClause+"and"+where;
                }
                num = db.delete("dict",whereClause,whereArgs);
                break;
            default:
                throw new IllegalArgumentException("未知的Uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        if(num>0){
            Toast.makeText(getContext(), "删除单词成功", Toast.LENGTH_SHORT).show();
        }
        return num;
    }
}
