package cn.ssdut.lst.supportdesignwidget;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lisongting on 2017/8/3.
 */

public class CoordinatorActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private String[] strs;
    private List<Map<String,String>> listMap;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        listView = (ListView) findViewById(R.id.listView);
        imageView = (ImageView) findViewById(R.id.header_img);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);

        Glide.with(this)
                .load(R.drawable.header_bg)
                .into(imageView);


//        collapsingToolbarLayout.setTitle("这是CollapsingToolbarLayout的Title");

    }

    @Override
    public void onResume() {
        super.onResume();
        listMap = new ArrayList<>();

        for(int i=0;i<20;i++) {
            String s = new StringBuilder("Item").append(i).toString();
            ArrayMap<String, String> map = new ArrayMap<>();
            map.put("text", s);
            listMap.add(map);
        }

        simpleAdapter = new SimpleAdapter(this, listMap
                , android.R.layout.simple_list_item_1
                , new String[]{"text"}, new int[]{android.R.id.text1});
        listView.setAdapter(simpleAdapter);

    }


}
