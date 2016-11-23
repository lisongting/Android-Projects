package com.test.administrator.dictprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/22.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL = "create table dict(_id Integer primary key autoincrement,word,detail)";
    public MyDatabaseHelper(Context context,String name,int version){
        super(context,name,null,version);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SQL);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
