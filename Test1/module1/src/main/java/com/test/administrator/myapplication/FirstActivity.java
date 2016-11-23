package com.test.administrator.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_first);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//书上说可以隐藏标题栏,然而测试结果并不能隐藏标题栏
        setContentView(R.layout.first_layout);
        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
              // Toast.makeText(FirstActivity.this,"启动第二个活动",Toast.LENGTH_SHORT).show();
              // Intent intent = new Intent(FirstActivity.this,SecondActivity.class);

              //  Intent intent = new Intent("dark_intent");
                //intent.addCategory("category");

               // Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse("http://www.qq.com"));

               // Intent intent = new Intent(Intent.ACTION_DIAL);
                // intent.setData(Uri.parse("tel:10086"));

                //String str = "Hello SecondActivity!";
                //Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                // intent.putExtra("extra_data",str);
                // startActivit(intent);

                //用StartActivityForResult()方法来启动活动二,测试接受活动二返回的resultCode
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                startActivityForResult(intent,1);//传入请求码1来启动intent

            }
        });
    }

    public boolean onOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.main,menu);//给当前活动创建菜单
    return true;
    }

    //用活动一的startActivityResult()来启动第二个活动后,活动二结束后会调用上一个方法的OnActivityResult方法,
    // 因此这里重写OnActivityResult方法来响应活动二结束
    protected void onActivityResult(int requestCode,int resultCode,Intent backIntent){
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String str = backIntent.getStringExtra("data_return");
                    Toast.makeText(FirstActivity.this,str,Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"you clicked ADD",Toast.LENGTH_LONG).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"you clicked Remove",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return true;
    }
}
