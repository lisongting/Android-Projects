package com.test.administrator.simpleadaptertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] names ={"苹果","香蕉","葡萄","芒果"};
    private String[] details={"被上帝咬过的苹果","可口的香蕉","玲珑剔透的水晶葡萄","最爱吃的芒果"};
    private int[] imagesId = {R.drawable.apple,R.drawable.banana,R.drawable.grape,R.drawable.mango};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Map<String,Object>> listItems = new ArrayList<>();
        for(int i=0;i<names.length;i++){
            Map<String,Object> listItem = new HashMap<>();
            listItem.put("header",imagesId[i]);
            listItem.put("name",names[i]);
            listItem.put("desc",details[i]);//创建4个map，把这5个map加到listItems容器中
            listItems.add(listItem);
        }
        //创建SimpleAdapter对象:
        //5个参数

        SimpleAdapter simpleadapter = new SimpleAdapter(this,listItems,R.layout.simple_item,
                new String[]{"name","header","desc"},new int[]{R.id.name,R.id.head,R.id.detail});
        ListView list = (ListView)findViewById(R.id.myList);
        list.setAdapter(simpleadapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("tag",names[position]+"被点击了-------------");
            }
        });

        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>parent,View view,int position,long id){
                Log.d("tag",names[position]+"被选中了-------------");
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });


    }
}
