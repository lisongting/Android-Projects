package com.test.administrator.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button bn;
    ListView listView;
    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建或打开数据库
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/my.db3",null);
        //Toast.makeText(MainActivity.this,this.getFilesDir().toString(),Toast.LENGTH_SHORT).show();
        TextView text1 = (TextView)findViewById(R.id.textView);
        text1.setText("数据库存放位置： "+this.getFilesDir().toString());
        listView = (ListView)findViewById(R.id.show);
        bn = (Button)findViewById(R.id.ok);
        editText1 = (EditText)findViewById(R.id.title);
        editText2 = (EditText)findViewById(R.id.content);
        bn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String  title =  editText1.getText().toString();
                String content = editText2.getText().toString();
                try{
                    insertData(db,title,content);
                    Cursor cursor = db.rawQuery("select *from news_inf",null);
                    inflateList(cursor);
                }catch(SQLiteException e){
                    db.execSQL("create table news_inf(_id integer primary key autoincrement,"
                            +"news_title varchar(50),"
                            +"news_content varchar(255));");
                    insertData(db,title,content);
                    Cursor cursor = db.rawQuery("select * from news_inf",null);
                    inflateList(cursor);
                }
            }
        });
    }

    private void insertData(SQLiteDatabase db,String title,String content){
        db.execSQL("insert into news_inf values(null,?,?)",new String[]{title,content});
    }

    private void inflateList(Cursor cursor){
        //根据查询到的结果集将其创建一个Adapter,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER的作用是将cursor封装成SimpleCursorAdapter
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.line,cursor,
                new String[]{"news_title","news_content"},new int[]{R.id.my_title,R.id.my_content},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    public void onDestroy(){
        super.onDestroy();
        if(db!=null&&db.isOpen()){
            db.close();
        }
    }
}
