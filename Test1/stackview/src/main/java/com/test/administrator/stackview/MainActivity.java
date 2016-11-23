package com.test.administrator.stackview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.StackView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private StackView stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] images = {R.drawable.apple,R.drawable.banana,R.drawable.carroit,R.drawable.cherry
                        ,R.drawable.grape,R.drawable.mango,R.drawable.orange,R.drawable.pear};
        stackView = (StackView)findViewById(R.id.mStackView);
        List<Map<String,Object>> listItems = new ArrayList<>();
        for(int i=0;i<images.length;i++){
            Map<String,Object> temp= new HashMap<>();
            temp.put("image",images[i]);
            listItems.add(temp);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,R.layout.cell,
                new String[]{"image"},new int[]{R.id.image1});
        stackView.setAdapter(simpleAdapter);
    }
    public void prev(View v){
        stackView.showPrevious();
    }
    public void next(View v){
        stackView.showNext();
    }
}
