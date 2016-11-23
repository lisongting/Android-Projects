package com.test.administrator.contactprovidertest;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/24.
 */
//短信监听器
public class SmsObserver extends ContentObserver {
    private Context context;
    //传入一个上下文对象来调用getContentResolver()方法
    public SmsObserver(Context c,Handler handler){
        super(handler);
        context = c;

    }

    //重写onChange方法，当被监听的Uri发生变化时，调用这个onChange()方法
    public void onChange(boolean selfChange){
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/outbox"),null,null,null,null);
        //遍历查询得到的结果集，可获得用户正在发送的短信
        while(cursor.moveToNext()){
            StringBuilder sb = new StringBuilder();
            sb.append("address=").append(cursor.getString(cursor.getColumnIndex("address")));
            sb.append("; subject=").append(cursor.getString(cursor.getColumnIndex("subject")));
            sb.append("; body=").append(cursor.getString(cursor.getColumnIndex("body")));
            sb.append("; time=").append(cursor.getString(cursor.getColumnIndex("date")));
            System.out.println("发送短信："+sb.toString());
            Log.d("tag","发送短信："+sb.toString());
            Toast.makeText(context, "发送短信"+sb.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
