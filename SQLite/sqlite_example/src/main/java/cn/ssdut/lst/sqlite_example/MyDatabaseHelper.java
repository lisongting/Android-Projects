package cn.ssdut.lst.sqlite_example;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class MyDatabaseHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE = "create table dict(" +
            "_id integer primary key autoincrement,word,detail)";
    public MyDatabaseHelper(Context context, String name, int version){
        super(context,name,null,version);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        Log.d("tag","-------onUpdate Called-------"+oldVersion+"------->"+newVersion);
    }
}
