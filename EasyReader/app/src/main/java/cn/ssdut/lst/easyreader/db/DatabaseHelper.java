package cn.ssdut.lst.easyreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/28.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建zhihu的数据库
        db.execSQL("create table if not exists Zhihu("
                +"id integer primary key autoincrement,"
                +"zhihu_id integer not null,"
                +"zhihu_news text,"
                +"zhihu_time real,"
                +"zhihu_content text)");
        db.execSQL("create table if not exists Guokr("
                +"id integer primary key autoincrement,"
                +"guokr_id integer not null,"
                +"guokr_news text,"
                +"guokr_time real,"
                +"guokr_content text)");
        //real是浮点类型
        db.execSQL("create table if not exists Douban("
                +"id integer primary key autoincrement,"
                +"douban_id integer not null,"
                +"douban_news text,"
                +"douban_time real,"
                +"douban_content text)");

        //添加书签
        db.execSQL("alter table Zhihu add column bookmark integer default 0");
        db.execSQL("alter table Guokr add column bookmark integer default 0");
        db.execSQL("alter table Douban add column bookmark integer default 0");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int i1) {
        switch (oldV) {
            case 1:

                break;
            case 2:
                db.execSQL("create table if not exists Contents(id integer primary key," +
                        "date integer not null,content text not null)");
                break;
            case 3:
                // delete table if exists
                //这两个表根本就没有创建啊
                db.execSQL("delete from LatestPosts");
                db.execSQL("delete from Contents");
                db.execSQL("drop table if exists LatestPosts");
                db.execSQL("drop table if exists Contents");

                // create a new table of zhihu daily
                db.execSQL("create table if not exists Zhihu("
                        + "id integer primary key autoincrement,"
                        + "zhihu_id integer not null,"
                        + "zhihu_news text,"
                        + "zhihu_time integer,"
                        + "zhihu_content text)");

                db.execSQL("create table if not exists Guokr("
                        + "id integer primary key autoincrement,"
                        + "guokr_id integer not null,"
                        + "guokr_news text,"
                        + "guokr_time integer,"
                        + "guokr_content text)");

                db.execSQL("create table if not exists Douban("
                        + "id integer primary key autoincrement,"
                        + "douban_id integer not null,"
                        + "douban_news text,"
                        + "douban_time integer,"
                        + "douban_content text)");
                break;
            case 4:
                db.execSQL("alter table Zhihu add column bookmark integer default 0");
                db.execSQL("alter table Guokr add column bookmark integer default 0");
                db.execSQL("alter table Douban add column bookmark integer default 0");
                break;

        }
    }
}
