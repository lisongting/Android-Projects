package com.ssdut.lst.namemaker;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/10/2.
 */
public class InitDatabaseThread extends Thread {
    private static final int DATA_ROW=600;
    private static final int DATA_COL=4;
    SQLiteDatabase db;
    String[] sqls;
    String[][] str;

    public InitDatabaseThread(SQLiteDatabase db, String[][] str){
        this.db=db; this.str = str;
        sqls = new String[DATA_ROW];
    }

    //向数据库中插入数据
    public void run(){
        for(int i=0;i<sqls.length;i++){
            if(str[i][0]!=null){
                sqls[i] = "insert into names values(null,?,?,?,?)";
                Object[] tmpParam = new Object[DATA_COL];
                tmpParam[0] = str[i][0];
                tmpParam[1] = str[i][1];
                tmpParam[2] = str[i][2];
                tmpParam[3] = str[i][3];
                db.execSQL(sqls[i],tmpParam);
            }else {
                break;
            }
        }

    }
}
