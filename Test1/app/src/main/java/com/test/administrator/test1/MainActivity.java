package com.test.administrator.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        super.setContentView(layout);//设置该activity显示layout
        layout.setOrientation(LinearLayout.VERTICAL);//设置布局的分布方向
        final TextView show = new TextView(this);//创建一个TextView
        Button bn = new Button(this);
        bn.setText("单击我");
        bn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(show);//向layout容器中添加TextView
        layout.addView(bn);//添加button
        bn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                show.setText("hello,android,"+new java.util.Date());
            }
        });

        //setContentView(R.layout.activity_main);
    }

}
