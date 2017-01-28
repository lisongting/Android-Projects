package cn.ssdut.lst.book_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/1/28.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private final String CREATE_TABLE =
            "create table if not exists bookShelf(_id Integer primary key autoincrement," +
                    "bookName varchar(50),author varchar(20))";

    public MyDatabaseHelper(Context ctx,String name,int version) {
        super(ctx, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
