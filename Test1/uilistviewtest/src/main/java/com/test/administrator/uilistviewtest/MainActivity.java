 package com.test.administrator.uilistviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {
//    private String[] data={"apple","banana","orange","watermelon",
//            "Pear","Grape","Pineapple","StrawBerry","Cherry","Mango","triblegrass","carroit"};
     private List<Fruit> fruitList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);
        ListView listview = (ListView)findViewById(R.id.list_view);
        listview.setAdapter(adapter);
        //为ListView中的条目添加点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this,"你点击了"+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;
        Toast.makeText(MainActivity.this,"xdpi:"+xdpi+" ydpi:"+ydpi,Toast.LENGTH_LONG).show();
        System.out.println("xdpi:"+xdpi+" ydpi:"+ydpi);

    }
     public void initFruits(){
         Fruit apple = new Fruit("Apple",R.drawable.apple);
         fruitList.add(apple);
         Fruit banana = new Fruit("Banana",R.drawable.banana);
         fruitList.add(banana);
         Fruit carroit = new Fruit("Carroit",R.drawable.carroit);
         fruitList.add(carroit);
         Fruit cherry = new Fruit("Cherry",R.drawable.cherry);
         fruitList.add(cherry);
         Fruit grape = new Fruit("Grape",R.drawable.grape);
         fruitList.add(grape);
         Fruit mango = new Fruit("Mango",R.drawable.mango);
         fruitList.add(mango);
         Fruit orange = new Fruit("Orange",R.drawable.orange);
         fruitList.add(orange);
         Fruit pear = new Fruit("Pear",R.drawable.pear);
         fruitList.add(pear);
         Fruit pineapple = new Fruit("Pineapple",R.drawable.pineapple);
         fruitList.add(pineapple);
         Fruit strawberry = new Fruit("Strawberry",R.drawable.strawberry);
         fruitList.add(strawberry);
         Fruit triblegrass = new Fruit("Triblegrass",R.drawable.triblegrass);
         fruitList.add(triblegrass);
         Fruit watermelon = new Fruit("Watermelon",R.drawable.watermelon);
         fruitList.add(watermelon);

     }
}
