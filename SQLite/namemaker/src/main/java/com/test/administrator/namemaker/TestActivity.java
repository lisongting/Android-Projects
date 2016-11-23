package com.test.administrator.namemaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/2.
 */

public class TestActivity extends AppCompatActivity {
    BundleList bdlist;
    ArrayList<Map<String,Object>> arrayList;
    ListView show;
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.test_layout);
        show = (ListView)findViewById(R.id.listView);
        bdlist = (BundleList)getIntent().getSerializableExtra("data");

        arrayList = bdlist.getList();
        SimpleAdapter adapter  = new SimpleAdapter(this,arrayList,R.layout.test_items,
                new String[]{"_id","_gender","_name","_detail","_key"},
                new int[]{R.id._id,R.id._gender,R.id._name,R.id._detail,R.id._key});
        show.setAdapter(adapter);
    }
}
