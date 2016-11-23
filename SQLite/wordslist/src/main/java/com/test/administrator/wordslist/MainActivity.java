package com.test.administrator.wordslist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"myDict.db3",1);
    }

    public void insertData(SQLiteDatabase db, String word, String detail){
        db.execSQL("insert into dict values(null,?,?)",new String[]{word,detail});
    }

    public void insert(View source){
        String word = ((EditText) findViewById(R.id.word)).getText().toString();
        String define = ((EditText) findViewById(R.id.detail)).getText().toString();
        insertData(dbHelper.getReadableDatabase(), word, define);
        Toast.makeText(MainActivity.this, "插入词条成功", Toast.LENGTH_SHORT).show();
    }

    public void search(View source){
        String key = ((EditText) findViewById(R.id.key)).getText().toString();
        String sql = "select * from dict where word like ? or detail like ?";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql,new String[]{"%"+key+"%","%"+key+"%"});
        //建立一个Bundle对象，通过这个对象将结果数据发送给Dialog中去
        Bundle data = new Bundle();
        data.putSerializable("data",converCursorToList(cursor));

        Intent intent = new Intent(MainActivity.this,ResultActivity.class);
        intent.putExtras(data);
        startActivity(intent);
    }

    //把结果集放到一个List中
    protected ArrayList<Map<String,String>> converCursorToList(Cursor cursor){
        ArrayList<Map<String,String>> result = new ArrayList<>();
        while(cursor.moveToNext()){
            Map<String,String> map = new HashMap<>();
            map.put("word",cursor.getString(1));
            map.put("detail",cursor.getString(2));
            result.add(map);
        }
        return result;
    }

}
