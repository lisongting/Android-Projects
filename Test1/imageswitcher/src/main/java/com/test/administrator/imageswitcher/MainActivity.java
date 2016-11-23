package com.test.administrator.imageswitcher;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    int[] imagesId = {R.drawable.apple,R.drawable.banana,R.drawable.carroit,R.drawable.cherry,
            R.drawable.grape,R.drawable.mango,R.drawable.orange,R.drawable.pear,R.drawable.pineapple,
            R.drawable.strawberry,R.drawable.triblegrass,R.drawable.watermelon};
    ImageSwitcher switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Map<String,Object>> listItems = new ArrayList<>();
        for(int i=0;i<imagesId.length;i++){
            Map<String,Object>  temp = new HashMap<>();
            temp.put("image",imagesId[i]);
            listItems.add(temp);
        }
        switcher = (ImageSwitcher)findViewById(R.id.switcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });

        SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.cell,
                new String[]{"image"},new int[]{R.id.image1});
        GridView grid =(GridView)findViewById(R.id.grid1);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>parent ,View view,int position,long id){
                switcher.setImageResource(imagesId[position]);
            }
        });
    }
}
