package com.test.administrator.dictresolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
    }
    public void insert(View v){
        String word = ((EditText)findViewById(R.id.word)).getText().toString();
        String detail =((EditText)findViewById(R.id.detail)).getText().toString();
        ContentValues values = new ContentValues();
        values.put(Words.Word.WORD,word);
        values.put(Words.Word.DETAIL,detail);
        resolver.insert(Words.Word.DICT_CONTENT_URI,values);
        Toast.makeText(MainActivity.this, "添加生词成功", Toast.LENGTH_SHORT).show();
    }

    public void search(View v){
        String key = ((EditText)findViewById(R.id.key)).getText().toString();
        Cursor cursor = resolver.query(Words.Word.DICT_CONTENT_URI,null,"word like ? or detail like ?",
                new String[]{"%"+key+"%","%"+key+"%"},null);
        Bundle data = new Bundle();
        data.putSerializable("data",convertCursorToList(cursor));
        Intent intent = new Intent(MainActivity.this,ResultActivity.class);
        intent.putExtras(data);
        startActivity(intent);
    }
    private ArrayList<Map<String,String>> convertCursorToList(Cursor c){
        ArrayList<Map<String,String>> items = new ArrayList<>();
        while(c.moveToNext()){
            Map<String,String> map = new HashMap<>();
            map.put(Words.Word.WORD,c.getString(1));//第二列
            map.put(Words.Word.DETAIL,c.getString(2));//第3列
            items.add(map);
        }
        return items;
    }
}
