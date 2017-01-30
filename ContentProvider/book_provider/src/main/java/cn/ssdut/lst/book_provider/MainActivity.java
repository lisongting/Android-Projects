package cn.ssdut.lst.book_provider;

import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView show;
    private Button btn;
    private SQLiteOpenHelper dbHelper;
    SQLiteDatabase database ;
    String SQL_QUERY = "select * from bookShelf";
    Cursor result ;
    List<Map<String,String>> list ;
    public static ContentObserver observer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.bt_show);
        show = (ListView) findViewById(R.id.listView);
        dbHelper = MyProvider.dbHelper;
        database = dbHelper.getReadableDatabase();
        observer = new MyObserver(this, new Handler());
        this.getContentResolver().registerContentObserver(
                Uri.parse("content://cn.ssdut.lst.MyProvider/books"),true,observer);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                result = database.rawQuery(SQL_QUERY, null);
                list = convertToList(result);
                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
                        list, R.layout.line,
                        new String[]{"id", "name", "author"},
                        new int[]{R.id.book_id, R.id.book_name, R.id.book_author});
                show.setAdapter(adapter);
            }
        });
    }
    public List<Map<String, String>> convertToList(Cursor cursor) {
        List<Map<String, String>> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String, String> item = new HashMap<>();
            item.put("id", cursor.getString(0));
            item.put("name", cursor.getString(1));
            item.put("author", cursor.getString(2));
            items.add(item);
        }
        return items;
    }
}
