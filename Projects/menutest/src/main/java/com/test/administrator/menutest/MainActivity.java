package com.test.administrator.menutest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int MENU1 = 111;
    final int MENU2 = 222;
    final int MENU3 = 333;
    private TextView tv;
    private PopupMenu pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textview);
        //为文本框添加上下文菜单，可通过长按来触发上下文菜单
        registerForContextMenu(tv);
    }
    @Override
    //通过重写这个方法创建上下文菜单
    public void onCreateContextMenu(ContextMenu menu, View source,ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,MENU1,0,"红色");
        menu.add(0,MENU2,0,"绿色");
        menu.add(0,MENU3,0,"蓝色");
        menu.setGroupCheckable(0,true,true);
        menu.setHeaderTitle("选择背景色");

    }

    //上下文菜单的选项被单机时触发该方法
    @Override
    public boolean onContextItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case MENU1:
                mi.setChecked(true);
                tv.setBackgroundColor(Color.RED);
                break;
            case MENU2:
                mi.setChecked(true);
                tv.setBackgroundColor(Color.GREEN);
                break;
            case MENU3:
                mi.setChecked(true);
                tv.setBackgroundColor(Color.BLUE);
                break;
        }
        return true;
    }

    public void popup(View v){
        pop = new PopupMenu(this,findViewById(R.id.button));
        getMenuInflater().inflate(R.menu.popup_menu,pop.getMenu());
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                switch(item.getItemId()){
                    case R.id.exit:
                        pop.dismiss();
                        break;
                    default:
                        Toast.makeText(MainActivity.this,"您点击了"+item.getTitle(),Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        pop.show();
    }
}
