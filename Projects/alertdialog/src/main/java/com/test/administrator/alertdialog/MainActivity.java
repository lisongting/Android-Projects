package com.test.administrator.alertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView show;
    private String[] items = {"塞纳河畔","左岸的咖","啡我手一杯","品尝你的美 ","留下唇印的嘴"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (TextView)findViewById(R.id.textview);
    }

    public void simple(View source){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("简单对话框")
                .setIcon(R.drawable.tools)
                .setMessage("对话框测试内容\n这是\t第二行");
                setPositiveButton(builder);
                setNegativeButton(builder)
                .create()
                .show();

    }

    public AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder){
        return builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                show.setText("单击了【确定】按钮");
            }
        });
    }

    public AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder){
        return builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                show.setText("单击了【取消】按钮");
            }
        });
    }

    //创建简单列表对话框
    public void simpleList(View source){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("简单列表对话框")
                .setIcon(R.drawable.tools)
                .setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        show.setText("你选中了:<"+items[which]+">");
                    }
                });
        setPositiveButton(builder);
        setNegativeButton(builder).create().show();
    }

    //创建单选列表项的对话框
    public void singleChoice(View source){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("简单单选列表对话框")
                .setIcon(R.drawable.tools)
                .setSingleChoiceItems(items,1,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog ,int which){
                        show.setText("你选中了:<"+items[which]+">");
                    }
                });
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    //创建多选列表对话框
    public void multiChoice(View source){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("多选列表对话框")
                .setIcon(R.drawable.tools)
                //设置勾选第二项，第四项
                .setMultiChoiceItems(items,new boolean[] {false,true,false,true,false},null);
        //为AlertDialog.Builder添加确定按钮
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create().show();

    }

    //创建自定义列表选项对话框
    public void customList(View source){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("自定义的对话框")
                .setIcon(R.drawable.tools)
                .setAdapter(new ArrayAdapter<String>(this,R.layout.array_item,items),null);
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    //创建自定义View对话框
    public void customView(View source){
        TableLayout loginForm = (TableLayout)getLayoutInflater().inflate(R.layout.login,null);
        new AlertDialog.Builder(this)
                .setTitle("自定义View对话框")
                .setIcon(R.drawable.tools)
                .setView(loginForm)
                .setPositiveButton("登陆",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        Toast.makeText(MainActivity.this,"正在登陆",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        Toast.makeText(MainActivity.this,"您取消了登陆",Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }

    //创建一个PopupWindow
    public void popupWindow(View source){
        View root = this.getLayoutInflater().inflate(R.layout.layout,null);
        //第一步，new一个popupWindow对象
        final PopupWindow popup = new PopupWindow(root,560,720);
        Button bt = (Button)root.findViewById(R.id.close_popupWindow);
        //第二步,调用showAsDropDown将popupWindow作为view组件的下拉组件显示出来(这是第一种调用方法)
        popup.showAsDropDown(source);
        //第二种调用方法，在指定位置显示
        //popup.showAtLocation(source, Gravity.CENTER,20,20);

        //获取PopupWindow中的关闭按钮
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popup.dismiss();//关闭popup窗口
            }
        });
    }

    public void progressDialogTest(View source){
        Intent intent = new Intent(MainActivity.this,ProgressDialogActivity.class);
        startActivity(intent);
    }
}
