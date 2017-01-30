package cn.ssdut.lst.book_provider;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.widget.Toast;
public class MyObserver extends ContentObserver {
    Context context;
    public MyObserver(Context context,Handler handler) {
        super(handler);
        this.context = context;
    }

    public void onChange(boolean selfChange) {
        Toast.makeText(context, "监听的数据发生了改变", Toast.LENGTH_LONG).show();
        super.onChange(selfChange);
    }
}
